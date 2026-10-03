package com.sanctus.christian.prayer.data.remote;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Community prayer-intention feed (posts/{id}, with amens/{uid} and reports/{uid} subcollections
 * that double as one-per-user guards). Counters are bumped client-side inside a batch together
 * with the guard doc, and firestore.rules only allows exactly that combination — so no paid
 * Cloud Function is needed. Reported/blocked content is filtered client-side.
 */
public class FeedRepository {
    private static final String POSTS = "posts";
    private static final String PREFS = "sanctus_prefs";
    private static final String KEY_BLOCKED = "blocked_uids";
    public static final int MAX_TEXT_LENGTH = 280;
    private static final int PAGE_SIZE = 50;
    /** Posts reported by this many distinct users are hidden for everyone. */
    private static final long HIDE_AFTER_REPORTS = 3;

    public interface PostsCallback { void onResult(List<CommunityPost> posts); }
    public interface DoneCallback { void onDone(boolean success); }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final SharedPreferences prefs;

    public FeedRepository(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
    }

    public Set<String> getBlockedUids() {
        return new HashSet<>(prefs.getStringSet(KEY_BLOCKED, new HashSet<>()));
    }

    public void blockUser(String uid) {
        Set<String> blocked = getBlockedUids();
        blocked.add(uid);
        prefs.edit().putStringSet(KEY_BLOCKED, blocked).apply();
    }

    /** Loads the latest posts, drops blocked/heavily-reported ones, and marks the viewer's own amens. */
    public void loadPosts(@Nullable String viewerUid, PostsCallback onResult, DoneCallback onFail) {
        db.collection(POSTS).orderBy("createdAt", Query.Direction.DESCENDING).limit(PAGE_SIZE).get()
            .addOnSuccessListener(qs -> {
                Set<String> blocked = getBlockedUids();
                List<CommunityPost> posts = new ArrayList<>();
                for (DocumentSnapshot doc : qs.getDocuments()) {
                    CommunityPost p = doc.toObject(CommunityPost.class);
                    if (p == null || p.text == null) continue;
                    if (p.reportCount >= HIDE_AFTER_REPORTS) continue;
                    if (p.authorUid != null && blocked.contains(p.authorUid)) continue;
                    p.id = doc.getId();
                    posts.add(p);
                }
                if (viewerUid == null || posts.isEmpty()) {
                    onResult.onResult(posts);
                    return;
                }
                markOwnAmens(viewerUid, posts, onResult);
            })
            .addOnFailureListener(e -> onFail.onDone(false));
    }

    private void markOwnAmens(String uid, List<CommunityPost> posts, PostsCallback onResult) {
        final int[] pending = {posts.size()};
        for (CommunityPost p : posts) {
            db.collection(POSTS).document(p.id).collection("amens").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        p.amenedByMe = task.getResult().exists();
                    }
                    if (--pending[0] == 0) onResult.onResult(posts);
                });
        }
    }

    public void createPost(String uid, @Nullable String displayName, String text, DoneCallback cb) {
        CommunityPost post = new CommunityPost(uid, displayName, text.trim(), System.currentTimeMillis());
        db.collection(POSTS).add(post)
            .addOnSuccessListener(ref -> cb.onDone(true))
            .addOnFailureListener(e -> cb.onDone(false));
    }

    public void addAmen(String uid, CommunityPost post, DoneCallback cb) {
        guardedIncrement(uid, post, "amens", "amenCount", cb);
    }

    public void report(String uid, CommunityPost post, DoneCallback cb) {
        guardedIncrement(uid, post, "reports", "reportCount", cb);
    }

    private void guardedIncrement(String uid, CommunityPost post, String sub, String counter, DoneCallback cb) {
        DocumentReference postRef = db.collection(POSTS).document(post.id);
        WriteBatch batch = db.batch();
        Map<String, Object> guard = new HashMap<>();
        guard.put("createdAt", System.currentTimeMillis());
        batch.set(postRef.collection(sub).document(uid), guard);
        batch.update(postRef, counter, FieldValue.increment(1));
        batch.commit()
            .addOnSuccessListener(v -> cb.onDone(true))
            .addOnFailureListener(e -> cb.onDone(false));
    }
}

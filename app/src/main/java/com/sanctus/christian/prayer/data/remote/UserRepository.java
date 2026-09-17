package com.sanctus.christian.prayer.data.remote;

import android.util.Log;
import androidx.annotation.Nullable;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.security.SecureRandom;

/**
 * Firestore-backed user profiles for the invite feature. Kept deliberately small (one
 * "users" collection, no subcollections) so a single signed-in user stays well inside the
 * Spark (free) plan's daily read/write quota.
 */
public class UserRepository {
    private static final String TAG = "UserRepository";
    private static final String COLLECTION = "users";
    private static final String CODE_ALPHABET = "ABCDEFGHJKMNPQRSTUVWXYZ23456789"; // no 0/O/1/I/L
    private static final SecureRandom RANDOM = new SecureRandom();

    public interface ProfileCallback {
        void onResult(@Nullable UserProfile profile);
    }

    public interface ErrorCallback {
        void onError(Exception e);
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    /**
     * Creates the user's profile document on first sign-in (no-op if it already exists).
     * If pendingReferralCode resolves to another user, links this account to them and bumps
     * the inviter's invite count.
     */
    public void ensureProfile(String uid, String displayName, String photoUrl,
                               @Nullable String pendingReferralCode,
                               ProfileCallback onDone, ErrorCallback onError) {
        db.collection(COLLECTION).document(uid).get()
            .addOnSuccessListener(snapshot -> {
                if (snapshot.exists()) {
                    onDone.onResult(snapshot.toObject(UserProfile.class));
                    return;
                }
                resolveInviter(pendingReferralCode, inviterUid ->
                    createProfile(uid, displayName, photoUrl, inviterUid, onDone, onError));
            })
            .addOnFailureListener(onError::onError);
    }

    private void createProfile(String uid, String displayName, String photoUrl,
                                @Nullable String inviterUid,
                                ProfileCallback onDone, ErrorCallback onError) {
        UserProfile profile = new UserProfile(displayName, photoUrl, System.currentTimeMillis(),
                generateReferralCode(), inviterUid);
        db.collection(COLLECTION).document(uid).set(profile)
            .addOnSuccessListener(v -> {
                if (inviterUid != null) incrementInviteCount(inviterUid);
                onDone.onResult(profile);
            })
            .addOnFailureListener(onError::onError);
    }

    private interface InviterCallback { void onResolved(@Nullable String inviterUid); }

    private void resolveInviter(@Nullable String referralCode, InviterCallback callback) {
        if (referralCode == null || referralCode.isEmpty()) {
            callback.onResolved(null);
            return;
        }
        db.collection(COLLECTION).whereEqualTo("referralCode", referralCode).limit(1).get()
            .addOnSuccessListener((QuerySnapshot qs) -> {
                if (qs.isEmpty()) {
                    callback.onResolved(null);
                } else {
                    DocumentSnapshot doc = qs.getDocuments().get(0);
                    callback.onResolved(doc.getId());
                }
            })
            .addOnFailureListener(e -> {
                Log.w(TAG, "Referral code lookup failed, continuing without attribution", e);
                callback.onResolved(null);
            });
    }

    private void incrementInviteCount(String inviterUid) {
        db.collection(COLLECTION).document(inviterUid)
            .update("inviteCount", FieldValue.increment(1))
            .addOnFailureListener(e -> Log.w(TAG, "Could not increment invite count for " + inviterUid, e));
    }

    public void getProfile(String uid, ProfileCallback onDone, ErrorCallback onError) {
        db.collection(COLLECTION).document(uid).get()
            .addOnSuccessListener(snapshot -> onDone.onResult(
                    snapshot.exists() ? snapshot.toObject(UserProfile.class) : null))
            .addOnFailureListener(onError::onError);
    }

    private static String generateReferralCode() {
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < 6; i++) {
            sb.append(CODE_ALPHABET.charAt(RANDOM.nextInt(CODE_ALPHABET.length())));
        }
        return sb.toString();
    }
}

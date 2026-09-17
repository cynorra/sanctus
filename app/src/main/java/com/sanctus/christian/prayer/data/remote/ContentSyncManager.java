package com.sanctus.christian.prayer.data.remote;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.dao.PrayerDao;
import com.sanctus.christian.prayer.data.local.dao.SaintDao;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;

import java.util.concurrent.Executors;

/**
 * Pulls prayer/saint content from Firestore (populated by scripts/sync-content-to-firestore.js,
 * run manually or via the sync-content GitHub Action) so fixes/additions reach installed apps
 * without a Play Store release. The bundled assets/seed/*.json (see SeedJsonLoader) remains the
 * offline-first baseline — this only overlays it once a network fetch succeeds.
 *
 * Cheap by design: one small "did anything change" read per app cold start
 * (content_meta/{prayers,saints}), and only pulls the full collection when the version differs
 * from what's already stored locally.
 */
public class ContentSyncManager {
    private static final String TAG = "ContentSyncManager";
    private static final String PREFS = "sanctus_prefs";

    public static void syncIfNeeded(Context context) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        checkAndSync(context.getApplicationContext(), db, "prayers");
        checkAndSync(context.getApplicationContext(), db, "saints");
    }

    private static void checkAndSync(Context context, FirebaseFirestore db, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        String localVersion = prefs.getString("content_version_" + key, null);

        db.collection("content_meta").document(key).get()
            .addOnSuccessListener(snap -> {
                String remoteVersion = snap.exists() ? snap.getString("version") : null;
                if (remoteVersion == null || remoteVersion.equals(localVersion)) return;

                if ("prayers".equals(key)) {
                    syncPrayers(context, db, remoteVersion);
                } else {
                    syncSaints(context, db, remoteVersion);
                }
            })
            .addOnFailureListener(e -> Log.w(TAG, "content_meta check failed for " + key, e));
    }

    private static void syncPrayers(Context context, FirebaseFirestore db, String remoteVersion) {
        db.collection("prayers").get()
            .addOnSuccessListener(qs -> Executors.newSingleThreadExecutor().execute(() -> {
                PrayerDao dao = SanctusDatabase.getInstance(context).prayerDao();
                for (QueryDocumentSnapshot doc : qs) {
                    String language = doc.getString("language");
                    String name = doc.getString("name");
                    if (language == null || name == null) continue;
                    String category = doc.getString("category");
                    String content = doc.getString("content");

                    int rows = dao.updateContent(language, name, category, content);
                    if (rows == 0) {
                        PrayerEntity e = new PrayerEntity();
                        e.language = language;
                        e.name = name;
                        e.category = category;
                        e.content = content;
                        e.isFavorite = false;
                        e.lastReadAt = 0;
                        dao.insertAll(e);
                    }
                }
                markSynced(context, "prayers", remoteVersion);
                Log.i(TAG, "Prayers synced to version " + remoteVersion);
            }))
            .addOnFailureListener(e -> Log.w(TAG, "prayers fetch failed", e));
    }

    private static void syncSaints(Context context, FirebaseFirestore db, String remoteVersion) {
        db.collection("saints").get()
            .addOnSuccessListener(qs -> Executors.newSingleThreadExecutor().execute(() -> {
                SaintDao dao = SanctusDatabase.getInstance(context).saintDao();
                for (QueryDocumentSnapshot doc : qs) {
                    String language = doc.getString("language");
                    String name = doc.getString("name");
                    String feastDate = doc.getString("feastDate");
                    if (language == null || name == null || feastDate == null) continue;
                    String description = doc.getString("description");
                    String patronOf = doc.getString("patronOf");

                    int rows = dao.updateContent(language, feastDate, name, description, patronOf);
                    if (rows == 0) {
                        SaintEntity e = new SaintEntity();
                        e.language = language;
                        e.name = name;
                        e.feastDate = feastDate;
                        e.description = description;
                        e.patronOf = patronOf;
                        e.isFavorite = false;
                        dao.insertAll(java.util.Collections.singletonList(e));
                    }
                }
                markSynced(context, "saints", remoteVersion);
                Log.i(TAG, "Saints synced to version " + remoteVersion);
            }))
            .addOnFailureListener(e -> Log.w(TAG, "saints fetch failed", e));
    }

    private static void markSynced(Context context, String key, String version) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit().putString("content_version_" + key, version).apply();
    }
}

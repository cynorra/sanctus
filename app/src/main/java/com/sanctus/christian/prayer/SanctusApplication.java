package com.sanctus.christian.prayer;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.sanctus.christian.prayer.util.Tradition;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.DatabaseInitializer;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.remote.ContentSyncManager;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.referral.InstallReferrerHelper;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.messaging.FirebaseMessaging;

public class SanctusApplication extends Application {

    private static final String TAG = "SanctusApp";
    private static SanctusApplication instance;
    private SanctusDatabase database;
    private SettingsRepository settingsRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override public void onActivityCreated(Activity activity, Bundle state) {
                // Runs inside super.onCreate, i.e. before the layout is inflated.
                SettingsRepository settings = getSettingsRepository();
                if (settings != null) {
                    // textAllCaps uses the default locale; a Turkish device with the app set to English
                    // would otherwise render "DEVOTIONAL" as "DEVOTİONAL".
                    java.util.Locale.setDefault(java.util.Locale.forLanguageTag(settings.getLanguage()));
                    activity.getTheme().applyStyle(Tradition.themeOverlay(settings.getTradition()), true);
                }
            }
            @Override public void onActivityStarted(Activity a) {}
            @Override public void onActivityResumed(Activity a) {}
            @Override public void onActivityPaused(Activity a) {}
            @Override public void onActivityStopped(Activity a) {}
            @Override public void onActivitySaveInstanceState(Activity a, Bundle s) {}
            @Override public void onActivityDestroyed(Activity a) {}
        });

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        // Capture any pending invite code from the Play Store install referrer (free, no
        // App Links/domain needed). Consumed later when the user actually signs in.
        InstallReferrerHelper.captureOnce(this);

        try {
            database = SanctusDatabase.getInstance(this);
            settingsRepository = new SettingsRepository(this);
        } catch (Exception e) {
            Log.e(TAG, "DB init failed", e);
        }

        // Notification channels
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                NotificationManager nm = getSystemService(NotificationManager.class);
                if (nm != null) {
                    NotificationChannel ch1 = new NotificationChannel(
                            "prayer_times", "Canonical Hours",
                            NotificationManager.IMPORTANCE_HIGH);
                    ch1.setDescription("Daily canonical hour prayer reminders");
                    ch1.enableVibration(true);
                    nm.createNotificationChannel(ch1);

                    NotificationChannel ch2 = new NotificationChannel(
                            "fcm_push_notifications", "Announcements & Daily Inspiration",
                            NotificationManager.IMPORTANCE_HIGH);
                    ch2.setDescription("Push notifications for daily verses, saints, and updates");
                    ch2.enableVibration(true);
                    nm.createNotificationChannel(ch2);
                }
            } catch (Exception e) {
                Log.e(TAG, "Notification channel failed", e);
            }
        }

        // Seed DB (bundled JSON — offline-first baseline)
        try {
            DatabaseInitializer.seed(this);
        } catch (Exception e) {
            Log.e(TAG, "DB seed failed", e);
        }

        // Pull any newer prayer/saint content from Firestore, if reachable. Best-effort: the
        // bundled seed above already leaves the app fully usable offline.
        try {
            ContentSyncManager.syncIfNeeded(this);
        } catch (Exception e) {
            Log.e(TAG, "Content sync failed", e);
        }

        // Alarms
        try {
            if (settingsRepository != null && settingsRepository.isNotificationsEnabled()) {
                PrayerAlarmManager.scheduleAll(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "Alarm schedule failed", e);
        }

        // Engagement push topic (see scripts/send-daily-engagement-notification.js +
        // .github/workflows/send-engagement-notification.yml for the sender side).
        try {
            if (settingsRepository != null && settingsRepository.isNotificationsEnabled()) {
                FirebaseMessaging.getInstance().subscribeToTopic(ENGAGEMENT_TOPIC);
            }
        } catch (Exception e) {
            Log.e(TAG, "Engagement topic subscribe failed", e);
        }
    }

    public static final String ENGAGEMENT_TOPIC = "engagement";

    public static SanctusApplication getInstance() { return instance; }
    public SanctusDatabase getDatabase() { return database; }

    public SettingsRepository getSettingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = new SettingsRepository(this);
        }
        return settingsRepository;
    }
}

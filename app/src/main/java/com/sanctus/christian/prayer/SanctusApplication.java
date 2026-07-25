package com.sanctus.christian.prayer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;
import com.sanctus.christian.prayer.data.local.DatabaseInitializer;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.google.android.gms.ads.MobileAds;

public class SanctusApplication extends Application {

    private static final String TAG = "SanctusApp";
    private static SanctusApplication instance;
    private SanctusDatabase database;
    private SettingsRepository settingsRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});

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
                NotificationChannel ch = new NotificationChannel(
                        "prayer_times", "Canonical Hours",
                        NotificationManager.IMPORTANCE_HIGH);
                ch.setDescription("Daily canonical hour prayer reminders");
                ch.enableVibration(true);
                nm.createNotificationChannel(ch);
            } catch (Exception e) {
                Log.e(TAG, "Notification channel failed", e);
            }
        }

        // Seed DB
        try {
            DatabaseInitializer.seed(this);
        } catch (Exception e) {
            Log.e(TAG, "DB seed failed", e);
        }

        // Alarms
        try {
            if (settingsRepository != null && settingsRepository.isNotificationsEnabled()) {
                PrayerAlarmManager.scheduleAll(this);
            }
        } catch (Exception e) {
            Log.e(TAG, "Alarm schedule failed", e);
        }
    }

    public static SanctusApplication getInstance() { return instance; }
    public SanctusDatabase getDatabase() { return database; }

    public SettingsRepository getSettingsRepository() {
        if (settingsRepository == null) {
            settingsRepository = new SettingsRepository(this);
        }
        return settingsRepository;
    }
}

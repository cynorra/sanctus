package com.sanctus.christian.prayer.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.entity.SettingsEntity;
import com.sanctus.christian.prayer.util.LanguageManager;
import com.sanctus.christian.prayer.widget.PrayerWidgetProvider;

public class SettingsRepository {
    private static final String PREFS_NAME = "sanctus_prefs";
    private final SharedPreferences prefs;
    private final SanctusDatabase db;
    private final Context appContext;
    private final MutableLiveData<String> languageLive = new MutableLiveData<>("en");

    public SettingsRepository(Context context) {
        appContext = context.getApplicationContext();
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        db = SanctusDatabase.getInstance(context);
        languageLive.setValue(getLanguage());

        // Apply the stored/derived language to the app's locale on cold start
        // (e.g. after this feature was added, or after a process restart)
        // so UI resource strings match without requiring the user to reopen Settings.
        if (AppCompatDelegate.getApplicationLocales().isEmpty()) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(getLanguage()));
        }
    }

    public String getLanguage() { return prefs.getString("language", LanguageManager.getSystemLocaleCode()); }
    public void setLanguage(String lang) {
        prefs.edit().putString("language", lang).apply();
        languageLive.setValue(lang);
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang));
    }

    public LiveData<String> getLanguageLive() { return languageLive; }

    /** One of the {@link com.sanctus.christian.prayer.util.Tradition} ids; GENERAL until the user picks one. */
    public String getTradition() { return prefs.getString("tradition", com.sanctus.christian.prayer.util.Tradition.GENERAL); }
    public void setTradition(String tradition) { prefs.edit().putString("tradition", tradition).apply(); }
    public boolean isOnboardingDone() { return prefs.getBoolean("onboarding_done", false); }
    public void setOnboardingDone(boolean done) { prefs.edit().putBoolean("onboarding_done", done).apply(); }
    public boolean isDarkMode() { return prefs.getBoolean("dark_mode", false); }
    public void setDarkMode(boolean dark) { prefs.edit().putBoolean("dark_mode", dark).apply(); }
    public boolean isNotificationsEnabled() { return prefs.getBoolean("notifications", true); }
    public void setNotificationsEnabled(boolean enabled) { prefs.edit().putBoolean("notifications", enabled).apply(); }
    public int getTextSize() { return prefs.getInt("text_size", 16); }
    public void setTextSize(int size) { prefs.edit().putInt("text_size", size).apply(); }

    // --- Prayer streak ---
    // "Day index" = local calendar day number, timezone-adjusted, so a streak survives
    // DST/timezone changes without needing java.time (minSdk 24 has no desugaring configured).
    private long todayDayIndex() {
        long now = System.currentTimeMillis();
        long offsetMs = java.util.TimeZone.getDefault().getOffset(now);
        return (now + offsetMs) / 86400000L;
    }

    /** Current streak for display: 0 if the user missed yesterday and today. */
    public int getStreak() {
        long last = prefs.getLong("streak_last_day", Long.MIN_VALUE);
        int count = prefs.getInt("streak_count", 0);
        return (todayDayIndex() - last <= 1) ? count : 0;
    }

    /** Call when the user engages with a prayer/rosary. No-ops if already recorded today. */
    public void recordPrayerActivityToday() {
        long today = todayDayIndex();
        long last = prefs.getLong("streak_last_day", Long.MIN_VALUE);
        long diff = today - last;
        if (diff == 0) return;
        int count = prefs.getInt("streak_count", 0);
        int newCount = (diff == 1) ? count + 1 : 1;
        prefs.edit().putLong("streak_last_day", today).putInt("streak_count", newCount).apply();
        PrayerWidgetProvider.refreshAll(appContext);
    }
}

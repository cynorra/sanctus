package com.sanctus.christian.prayer.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.entity.SettingsEntity;

public class SettingsRepository {
    private static final String PREFS_NAME = "sanctus_prefs";
    private final SharedPreferences prefs;
    private final SanctusDatabase db;
    private final MutableLiveData<String> languageLive = new MutableLiveData<>("en");

    public SettingsRepository(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        db = SanctusDatabase.getInstance(context);
        languageLive.setValue(getLanguage());
    }

    public String getLanguage() { return prefs.getString("language", "en"); }
    public void setLanguage(String lang) {
        prefs.edit().putString("language", lang).apply();
        languageLive.setValue(lang);
    }

    public LiveData<String> getLanguageLive() { return languageLive; }
    public boolean isDarkMode() { return prefs.getBoolean("dark_mode", false); }
    public void setDarkMode(boolean dark) { prefs.edit().putBoolean("dark_mode", dark).apply(); }
    public boolean isNotificationsEnabled() { return prefs.getBoolean("notifications", true); }
    public void setNotificationsEnabled(boolean enabled) { prefs.edit().putBoolean("notifications", enabled).apply(); }
    public String getLocationCity() { return prefs.getString("location_city", ""); }
    public void setLocationCity(String city) { prefs.edit().putString("location_city", city).apply(); }
    public double getLocationLat() { return Double.longBitsToDouble(prefs.getLong("lat", Double.doubleToLongBits(0))); }
    public double getLocationLng() { return Double.longBitsToDouble(prefs.getLong("lng", Double.doubleToLongBits(0))); }
    public void setLocation(String city, double lat, double lng) {
        prefs.edit()
                .putString("location_city", city)
                .putLong("lat", Double.doubleToLongBits(lat))
                .putLong("lng", Double.doubleToLongBits(lng))
                .apply();
    }
    public int getTextSize() { return prefs.getInt("text_size", 16); }
    public void setTextSize(int size) { prefs.edit().putInt("text_size", size).apply(); }
}

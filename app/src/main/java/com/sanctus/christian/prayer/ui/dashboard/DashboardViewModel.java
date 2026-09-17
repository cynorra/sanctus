package com.sanctus.christian.prayer.ui.dashboard;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import java.util.Calendar;
import java.util.List;

public class DashboardViewModel extends AndroidViewModel {
    private final PrayerRepository prayerRepository;
    private final SettingsRepository settingsRepository;

    public DashboardViewModel(Application app) {
        super(app);
        prayerRepository = new PrayerRepository(app);
        settingsRepository = SanctusApplication.getInstance().getSettingsRepository();
    }

    public LiveData<List<PrayerEntity>> getFavoritePrayers() {
        return prayerRepository.getFavorites(getApplication().getSharedPreferences("sanctus_prefs", android.content.Context.MODE_PRIVATE).getString("language", "en"));
    }

    public String getLanguage() {
        return settingsRepository.getLanguage();
    }

    public int getStreak() {
        return settingsRepository.getStreak();
    }

    /**
     * A different prayer spotlighted each day (deterministic by day-of-year, not random), so the
     * dashboard has rotating content beyond the always-the-same canonical hours strip. Falls back
     * to the English library if the user's language has none yet.
     */
    public LiveData<PrayerEntity> getPrayerOfTheDay() {
        int dayOfYear = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        String lang = settingsRepository.getLanguage();
        LiveData<List<PrayerEntity>> primary = prayerRepository.getPrayers(lang);
        if ("en".equals(lang)) {
            return Transformations.map(primary, list -> pickForDay(list, dayOfYear));
        }
        return Transformations.switchMap(primary, list -> {
            if (list != null && !list.isEmpty()) return staticLiveData(pickForDay(list, dayOfYear));
            return Transformations.map(prayerRepository.getPrayers("en"), l -> pickForDay(l, dayOfYear));
        });
    }

    private static PrayerEntity pickForDay(List<PrayerEntity> list, int dayOfYear) {
        if (list == null || list.isEmpty()) return null;
        return list.get(dayOfYear % list.size());
    }

    private static <T> LiveData<T> staticLiveData(T value) {
        MutableLiveData<T> live = new MutableLiveData<>();
        live.setValue(value);
        return live;
    }
}

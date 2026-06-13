package com.sanctus.christian.prayer.ui.dashboard;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
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
}

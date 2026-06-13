package com.sanctus.christian.prayer.ui.prayer;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import java.util.List;

public class PrayerViewModel extends AndroidViewModel {
    private final PrayerRepository repository;
    private final String language;

    public PrayerViewModel(Application app) {
        super(app);
        repository = new PrayerRepository(app);
        language = SanctusApplication.getInstance().getSettingsRepository().getLanguage();
    }

    public LiveData<List<PrayerEntity>> getPrayers() {
        return repository.getPrayers(language);
    }

    public LiveData<List<PrayerEntity>> getPrayersByCategory(String category) {
        return repository.getPrayersByCategory(category, language);
    }
}

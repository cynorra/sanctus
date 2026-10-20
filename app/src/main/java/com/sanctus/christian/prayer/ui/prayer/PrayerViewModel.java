package com.sanctus.christian.prayer.ui.prayer;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.repository.PrayerRepository;
import java.util.List;

public class PrayerViewModel extends AndroidViewModel {
    private final PrayerRepository repository;
    private final String language;
    private final MutableLiveData<Boolean> usingFallbackLanguage = new MutableLiveData<>(false);

    public PrayerViewModel(Application app) {
        super(app);
        repository = new PrayerRepository(app);
        language = SanctusApplication.getInstance().getSettingsRepository().getLanguage();
    }

    /** Falls back to English when the selected language has no prayers seeded yet. */
    public LiveData<List<PrayerEntity>> getPrayers() {
        LiveData<List<PrayerEntity>> primary = repository.getPrayers(language);
        if ("en".equals(language)) return primary;
        return Transformations.switchMap(primary, list -> {
            if (list == null || list.isEmpty() || onlyScripture(list)) {
                usingFallbackLanguage.setValue(true);
                return repository.getPrayers("en");
            }
            usingFallbackLanguage.setValue(false);
            return staticLiveData(list);
        });
    }

    public LiveData<Boolean> isUsingFallbackLanguage() { return usingFallbackLanguage; }

    public LiveData<List<PrayerEntity>> getPrayersByCategory(String category) {
        return repository.getPrayersByCategory(category, language);
    }

    public LiveData<List<PrayerEntity>> search(String query) {
        return repository.search(query, language);
    }

    public void toggleFavorite(PrayerEntity prayer) {
        repository.toggleFavorite(prayer);
    }

    /** English Bible passages are shown in every language, so they alone do not mean the language has content. */
    private static boolean onlyScripture(List<PrayerEntity> list) {
        for (PrayerEntity p : list) {
            if (!"scripture".equals(p.category)) return false;
        }
        return true;
    }

    private static LiveData<List<PrayerEntity>> staticLiveData(List<PrayerEntity> list) {
        MutableLiveData<List<PrayerEntity>> live = new MutableLiveData<>();
        live.setValue(list);
        return live;
    }
}

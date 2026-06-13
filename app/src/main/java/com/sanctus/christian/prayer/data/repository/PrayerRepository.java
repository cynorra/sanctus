package com.sanctus.christian.prayer.data.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.dao.PrayerDao;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import java.util.List;
import java.util.concurrent.Executors;

public class PrayerRepository {
    private final PrayerDao dao;

    public PrayerRepository(Context context) {
        dao = SanctusDatabase.getInstance(context).prayerDao();
    }

    public LiveData<List<PrayerEntity>> getPrayers(String language) { return dao.getPrayersByLanguage(language); }
    public LiveData<List<PrayerEntity>> getPrayersByCategory(String category, String language) { return dao.getPrayersByCategory(category, language); }
    public LiveData<List<PrayerEntity>> getFavorites(String language) { return dao.getFavoritePrayers(language); }
    public LiveData<PrayerEntity> getPrayerById(int id) { return dao.getPrayerById(id); }
    public LiveData<List<PrayerEntity>> search(String query, String language) { return dao.searchPrayers(query, language); }

    public void toggleFavorite(PrayerEntity prayer) {
        Executors.newSingleThreadExecutor().execute(() -> {
            prayer.isFavorite = !prayer.isFavorite;
            prayer.lastReadAt = System.currentTimeMillis();
            dao.update(prayer);
        });
    }

    public void insertAll(PrayerEntity... prayers) {
        Executors.newSingleThreadExecutor().execute(() -> dao.insertAll(prayers));
    }
}

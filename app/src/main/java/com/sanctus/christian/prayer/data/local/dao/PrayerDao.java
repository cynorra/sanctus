package com.sanctus.christian.prayer.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import java.util.List;

@Dao
public interface PrayerDao {
    @Query("SELECT * FROM prayers WHERE language = :lang ORDER BY name ASC")
    LiveData<List<PrayerEntity>> getPrayersByLanguage(String lang);

    @Query("SELECT * FROM prayers WHERE category = :category AND language = :lang")
    LiveData<List<PrayerEntity>> getPrayersByCategory(String category, String lang);

    @Query("SELECT * FROM prayers WHERE isFavorite = 1 AND language = :lang")
    LiveData<List<PrayerEntity>> getFavoritePrayers(String lang);

    @Query("SELECT * FROM prayers WHERE id = :id")
    LiveData<PrayerEntity> getPrayerById(int id);

    @Query("SELECT * FROM prayers WHERE (name LIKE '%' || :q || '%' OR content LIKE '%' || :q || '%') AND language = :lang")
    LiveData<List<PrayerEntity>> searchPrayers(String q, String lang);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(PrayerEntity... prayers);

    @Update
    void update(PrayerEntity prayer);

    @Query("SELECT COUNT(*) FROM prayers")
    int getCount();

    /** Returns rows affected — used by ContentSyncManager to decide update-vs-insert. */
    @Query("UPDATE prayers SET category = :category, content = :content WHERE language = :language AND name = :name")
    int updateContent(String language, String name, String category, String content);

    /** Synchronous lookup (call off the main thread) — used to resolve a push notification's
     *  deep link (which only knows the prayer's name/language, not the local row id) to an id. */
    @Query("SELECT * FROM prayers WHERE name = :name AND language = :language LIMIT 1")
    PrayerEntity findByNameAndLanguage(String name, String language);
}

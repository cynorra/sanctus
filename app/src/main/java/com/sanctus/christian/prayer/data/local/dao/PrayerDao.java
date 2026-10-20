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
    /** Bible passages exist only in English (public-domain KJV), so every language sees them. */
    @Query("SELECT * FROM prayers WHERE (language = :lang OR (:lang != 'en' AND category = 'scripture' AND language = 'en')) AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    LiveData<List<PrayerEntity>> getPrayersByLanguage(String lang, String tradition);

    @Query("SELECT * FROM prayers WHERE category = :category AND (language = :lang OR (:lang != 'en' AND category = 'scripture' AND language = 'en')) AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    LiveData<List<PrayerEntity>> getPrayersByCategory(String category, String lang, String tradition);

    @Query("SELECT * FROM prayers WHERE isFavorite = 1 AND language = :lang")
    LiveData<List<PrayerEntity>> getFavoritePrayers(String lang);

    @Query("SELECT * FROM prayers WHERE id = :id")
    LiveData<PrayerEntity> getPrayerById(int id);

    @Query("SELECT * FROM prayers WHERE (name LIKE '%' || :q || '%' OR content LIKE '%' || :q || '%') AND (language = :lang OR (:lang != 'en' AND category = 'scripture' AND language = 'en')) AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    LiveData<List<PrayerEntity>> searchPrayers(String q, String lang, String tradition);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(PrayerEntity... prayers);

    @Update
    void update(PrayerEntity prayer);

    /** Applies the tradition tag from the seed file to a row that already exists on this device. */
    @Query("UPDATE prayers SET tradition = :tradition WHERE language = :language AND name = :name")
    int updateTradition(String language, String name, String tradition);

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

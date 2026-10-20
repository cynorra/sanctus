package com.sanctus.christian.prayer.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import java.util.List;

@Dao
public interface SaintDao {
    @Query("SELECT * FROM saints WHERE language = :language AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    LiveData<List<SaintEntity>> getAllSaints(String language, String tradition);

    /** Blocking twin of {@link #getAllSaints} for the reminder receiver's featured-saint fallback. */
    @Query("SELECT * FROM saints WHERE language = :language AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    List<SaintEntity> listAll(String language, String tradition);

    /**
     * The saint for a feast date, restricted to the user's tradition. When several match, the one
     * tagged for exactly that tradition wins over one shared with others, which wins over "all".
     */
    @Query("SELECT * FROM saints WHERE feastDate = :date AND language = :language AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY CASE WHEN tradition = :tradition THEN 0 WHEN tradition = 'all' THEN 2 ELSE 1 END, name ASC LIMIT 1")
    LiveData<SaintEntity> getSaintByFeastDate(String date, String language, String tradition);

    /** Blocking twin of {@link #getSaintByFeastDate} for background callers such as the reminder receiver. */
    @Query("SELECT * FROM saints WHERE feastDate = :date AND language = :language AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY CASE WHEN tradition = :tradition THEN 0 WHEN tradition = 'all' THEN 2 ELSE 1 END, name ASC LIMIT 1")
    SaintEntity findForDate(String date, String language, String tradition);

    @Query("SELECT * FROM saints WHERE id = :id")
    LiveData<SaintEntity> getSaintById(int id);

    @Query("SELECT * FROM saints WHERE isFavorite = 1 AND language = :language")
    LiveData<List<SaintEntity>> getFavoriteSaints(String language);

    @Query("SELECT * FROM saints WHERE (name LIKE '%' || :query || '%' OR patronOf LIKE '%' || :query || '%') AND language = :language AND (:tradition = '' OR tradition = 'all' OR (',' || tradition || ',') LIKE '%,' || :tradition || ',%') ORDER BY name ASC")
    LiveData<List<SaintEntity>> searchSaints(String query, String language, String tradition);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<SaintEntity> saints);

    @Update
    void update(SaintEntity saint);

    /** Applies the tradition tag from the seed file to a row that already exists on this device. */
    @Query("UPDATE saints SET tradition = :tradition WHERE language = :language AND feastDate = :feastDate AND name = :name")
    int updateTradition(String language, String feastDate, String name, String tradition);

    @Query("SELECT COUNT(*) FROM saints")
    int getCount();

    /** Returns rows affected — used by ContentSyncManager to decide update-vs-insert. */
    @Query("UPDATE saints SET description = :description, patronOf = :patronOf WHERE language = :language AND feastDate = :feastDate AND name = :name")
    int updateContent(String language, String feastDate, String name, String description, String patronOf);
}

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
    @Query("SELECT * FROM saints WHERE language = :language ORDER BY name ASC")
    LiveData<List<SaintEntity>> getAllSaints(String language);

    @Query("SELECT * FROM saints WHERE feastDate = :date AND language = :language LIMIT 1")
    LiveData<SaintEntity> getSaintByFeastDate(String date, String language);

    @Query("SELECT * FROM saints WHERE id = :id")
    LiveData<SaintEntity> getSaintById(int id);

    @Query("SELECT * FROM saints WHERE isFavorite = 1 AND language = :language")
    LiveData<List<SaintEntity>> getFavoriteSaints(String language);

    @Query("SELECT * FROM saints WHERE (name LIKE '%' || :query || '%' OR patronOf LIKE '%' || :query || '%') AND language = :language")
    LiveData<List<SaintEntity>> searchSaints(String query, String language);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<SaintEntity> saints);

    @Update
    void update(SaintEntity saint);

    @Query("SELECT COUNT(*) FROM saints")
    int getCount();
}

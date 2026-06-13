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
    @Query("SELECT * FROM saints ORDER BY name ASC")
    LiveData<List<SaintEntity>> getAllSaints();

    @Query("SELECT * FROM saints WHERE feastDate = :date LIMIT 1")
    LiveData<SaintEntity> getSaintByFeastDate(String date);

    @Query("SELECT * FROM saints WHERE id = :id")
    LiveData<SaintEntity> getSaintById(int id);

    @Query("SELECT * FROM saints WHERE isFavorite = 1")
    LiveData<List<SaintEntity>> getFavoriteSaints();

    @Query("SELECT * FROM saints WHERE name LIKE '%' || :query || '%' OR patronOf LIKE '%' || :query || '%'")
    LiveData<List<SaintEntity>> searchSaints(String query);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<SaintEntity> saints);

    @Update
    void update(SaintEntity saint);

    @Query("SELECT COUNT(*) FROM saints")
    int getCount();
}

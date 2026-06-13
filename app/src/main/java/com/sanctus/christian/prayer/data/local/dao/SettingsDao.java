package com.sanctus.christian.prayer.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.sanctus.christian.prayer.data.local.entity.SettingsEntity;

@Dao
public interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    LiveData<SettingsEntity> getSettings();

    @Query("SELECT * FROM settings WHERE id = 1")
    SettingsEntity getSettingsSync();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SettingsEntity settings);

    @Update
    void update(SettingsEntity settings);
}

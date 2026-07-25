package com.sanctus.christian.prayer.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import java.util.List;

@Dao
public interface CalendarEventDao {
    @Query("SELECT * FROM calendar_events WHERE language = :language")
    LiveData<List<CalendarEventEntity>> getAllEvents(String language);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CalendarEventEntity> events);

    @Query("SELECT COUNT(*) FROM calendar_events")
    int getCount();
}

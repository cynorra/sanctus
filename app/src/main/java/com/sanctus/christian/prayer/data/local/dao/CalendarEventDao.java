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
    @Query("SELECT * FROM calendar_events WHERE date = :monthDay ORDER BY isSolemnity DESC")
    LiveData<List<CalendarEventEntity>> getEventsForDate(String monthDay);

    @Query("SELECT * FROM calendar_events WHERE isSolemnity = 1 OR isHolyDayOfObligation = 1 ORDER BY date ASC")
    LiveData<List<CalendarEventEntity>> getMajorFeast();

    @Query("SELECT * FROM calendar_events WHERE season = :season ORDER BY date ASC")
    LiveData<List<CalendarEventEntity>> getEventsBySeason(String season);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CalendarEventEntity> events);

    @Query("SELECT COUNT(*) FROM calendar_events")
    int getCount();
}

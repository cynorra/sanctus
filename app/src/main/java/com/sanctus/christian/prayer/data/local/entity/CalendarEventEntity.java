package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "calendar_events")
public class CalendarEventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String date;       // "MM-DD" format
    public String season;     // ADVENT, LENT, EASTER, CHRISTMAS, ORDINARY
    public String color;      // purple, white, green, red, rose
    public String description;
    public boolean isFeastDay;
    public boolean isSolemnity;
    public boolean isHolyDayOfObligation;
}

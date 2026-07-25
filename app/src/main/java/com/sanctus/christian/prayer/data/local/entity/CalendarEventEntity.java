package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "calendar_events", indices = {@Index(value = {"language", "date", "easterOffset", "title"}, unique = true)})
public class CalendarEventEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String date;       // "MM-DD" format; null for movable feasts (use easterOffset instead)
    public Integer easterOffset; // days relative to Easter Sunday; null for fixed-date events
    public String season;     // ADVENT, LENT, EASTER, CHRISTMAS, ORDINARY
    public String color;      // purple, white, green, red, rose
    public String description;
    public boolean isFeastDay;
    public boolean isSolemnity;
    public boolean isHolyDayOfObligation;
    public String language = "en";
}

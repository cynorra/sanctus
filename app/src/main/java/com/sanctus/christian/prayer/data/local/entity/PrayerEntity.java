package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "prayers")
public class PrayerEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String content;
    public String category;
    public String language;
    public boolean isFavorite;
    public long lastReadAt;
}

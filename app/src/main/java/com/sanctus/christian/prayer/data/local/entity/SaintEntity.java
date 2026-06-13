package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "saints")
public class SaintEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String feastDate;   // "June 13" format
    public String description;
    public String patronOf;
    public String imageUrl;
    public boolean isFavorite;
}

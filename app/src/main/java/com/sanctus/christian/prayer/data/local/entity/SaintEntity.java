package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "saints", indices = {@Index(value = {"language", "feastDate", "name"}, unique = true)})
public class SaintEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String feastDate;   // "June 13" format — canonical match key, always English
    public String description;
    public String patronOf;
    public String imageUrl;
    public boolean isFavorite;
    public String language = "en";
}

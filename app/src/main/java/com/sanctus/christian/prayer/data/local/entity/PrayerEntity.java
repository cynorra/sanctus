package com.sanctus.christian.prayer.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "prayers", indices = {@Index(value = {"language", "name"}, unique = true)})
public class PrayerEntity {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public String content;
    public String category;
    public String language;
    public boolean isFavorite;
    public long lastReadAt;
    /** {@code "all"} or a comma-separated list of tradition ids, see util.Tradition. */
    @NonNull
    @ColumnInfo(defaultValue = "all")
    public String tradition = "all";
}

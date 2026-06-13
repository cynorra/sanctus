package com.sanctus.christian.prayer.data.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "settings")
public class SettingsEntity {
    @PrimaryKey
    public int id = 1;
    public String language;
    public boolean darkMode;
    public boolean notificationsEnabled;
    public String locationCity;
    public double locationLat;
    public double locationLng;
    public int textSize;
}

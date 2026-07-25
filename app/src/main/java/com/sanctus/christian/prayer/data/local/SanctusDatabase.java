package com.sanctus.christian.prayer.data.local;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.sanctus.christian.prayer.data.local.dao.CalendarEventDao;
import com.sanctus.christian.prayer.data.local.dao.PrayerDao;
import com.sanctus.christian.prayer.data.local.dao.SaintDao;
import com.sanctus.christian.prayer.data.local.dao.SettingsDao;
import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.local.entity.SettingsEntity;
import com.sanctus.christian.prayer.data.local.migration.Migrations;

@Database(
    entities = {PrayerEntity.class, SettingsEntity.class, SaintEntity.class, CalendarEventEntity.class},
    version = 3,
    exportSchema = false
)
public abstract class SanctusDatabase extends RoomDatabase {
    private static volatile SanctusDatabase INSTANCE;

    public abstract PrayerDao prayerDao();
    public abstract SettingsDao settingsDao();
    public abstract SaintDao saintDao();
    public abstract CalendarEventDao calendarEventDao();

    public static SanctusDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SanctusDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            SanctusDatabase.class,
                            "sanctus_db"
                    ).addMigrations(Migrations.MIGRATION_2_3)
                     .fallbackToDestructiveMigrationOnDowngrade()
                     .build();
                }
            }
        }
        return INSTANCE;
    }
}

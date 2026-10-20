package com.sanctus.christian.prayer.data.local.migration;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Explicit Room migrations. We never fall back to destructive migration on
 * upgrade — existing users' favorited prayers/saints must survive app updates.
 */
public class Migrations {

    /** Adds the tradition tag; existing rows stay visible to everyone until the seed tags them. */
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE prayers ADD COLUMN tradition TEXT NOT NULL DEFAULT 'all'");
            db.execSQL("ALTER TABLE saints ADD COLUMN tradition TEXT NOT NULL DEFAULT 'all'");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase db) {
            db.execSQL("ALTER TABLE saints ADD COLUMN language TEXT NOT NULL DEFAULT 'en'");
            db.execSQL("ALTER TABLE calendar_events ADD COLUMN language TEXT NOT NULL DEFAULT 'en'");
            db.execSQL("ALTER TABLE calendar_events ADD COLUMN easterOffset INTEGER");

            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_prayers_language_name ON prayers(language, name)");
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_saints_language_feastDate_name ON saints(language, feastDate, name)");
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_calendar_events_language_date_easterOffset_title ON calendar_events(language, date, easterOffset, title)");
        }
    };
}

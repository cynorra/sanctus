package com.sanctus.christian.prayer.data.local;

import android.content.Context;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.seed.CalendarSeedData;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseInitializer {

    /**
     * Inserts all seed content on every app start. Prayers/saints/calendar_events each have a
     * unique index on their natural key, and inserts use OnConflictStrategy.IGNORE, so rows that
     * already exist are silent no-ops while newly added seed content (from an app update) reaches
     * existing installs too — not just fresh installs.
     *
     * Prayers/saints come from assets/seed/*.json (see SeedJsonLoader) rather than hardcoded Java,
     * so new content or fixes only need a new JSON file, not a recompile.
     */
    public static void seed(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SanctusDatabase db = SanctusDatabase.getInstance(context);

            List<PrayerEntity> prayers = SeedJsonLoader.loadPrayers(context);
            db.prayerDao().insertAll(prayers.toArray(new PrayerEntity[0]));

            List<SaintEntity> saints = SeedJsonLoader.loadSaints(context);
            db.saintDao().insertAll(saints);

            // Inserts above ignore rows that already exist, so rows seeded before the tradition
            // tag existed (or retagged since) need the tag applied explicitly.
            db.runInTransaction(() -> {
                for (PrayerEntity p : prayers) {
                    db.prayerDao().updateTradition(p.language, p.name, p.tradition);
                }
                for (SaintEntity s : saints) {
                    db.saintDao().updateTradition(s.language, s.feastDate, s.name, s.tradition);
                }
            });
            db.calendarEventDao().insertAll(CalendarSeedData.getAll());
        });
    }
}

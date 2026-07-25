package com.sanctus.christian.prayer.data.local;

import android.content.Context;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.seed.CalendarSeedData;
import com.sanctus.christian.prayer.data.seed.PrayerSeedData;
import com.sanctus.christian.prayer.data.seed.PrayerSeedDataExtra;
import com.sanctus.christian.prayer.data.seed.PrayerSeedDataAfrica;
import com.sanctus.christian.prayer.data.seed.SaintSeedData;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class DatabaseInitializer {

    /**
     * Inserts all seed content on every app start. Prayers/saints/calendar_events each have a
     * unique index on their natural key, and inserts use OnConflictStrategy.IGNORE, so rows that
     * already exist are silent no-ops while newly added seed content (from an app update) reaches
     * existing installs too — not just fresh installs.
     */
    public static void seed(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SanctusDatabase db = SanctusDatabase.getInstance(context);

            List<PrayerEntity> all = new ArrayList<>();
            all.addAll(PrayerSeedData.getAll());       // EN, TR, ES, PT
            all.addAll(PrayerSeedDataExtra.getAll());  // DE,FR,IT,PL,UK,RU,RO,NL,EL,FIL,ZH,HI,KO,ID,VI,SW,AM,HU,CS,SK,HR
            all.addAll(PrayerSeedDataAfrica.getAll()); // AR,HY,KA,TA,ML,TE,YO,IG
            db.prayerDao().insertAll(all.toArray(new PrayerEntity[0]));

            db.saintDao().insertAll(SaintSeedData.getAll());
            db.calendarEventDao().insertAll(CalendarSeedData.getAll());
        });
    }
}

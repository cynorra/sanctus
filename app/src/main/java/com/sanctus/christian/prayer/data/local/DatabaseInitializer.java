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

    public static void seedIfEmpty(Context context) {
        Executors.newSingleThreadExecutor().execute(() -> {
            SanctusDatabase db = SanctusDatabase.getInstance(context);

            if (db.prayerDao().getCount() == 0) {
                List<PrayerEntity> all = new ArrayList<>();
                all.addAll(PrayerSeedData.getAll());       // EN, TR, ES, PT (28)
                all.addAll(PrayerSeedDataExtra.getAll());  // DE,FR,IT,PL,UK,RU,RO,NL,EL,FIL,ZH,HI,KO,ID,VI,SW,AM,HU,CS,SK,HR (55)
                all.addAll(PrayerSeedDataAfrica.getAll()); // AR,HY,KA,TA,ML,TE,YO,IG (28)
                db.prayerDao().insertAll(all.toArray(new PrayerEntity[0]));
            }

            if (db.saintDao().getCount() == 0) {
                db.saintDao().insertAll(SaintSeedData.getAll());
            }

            if (db.calendarEventDao().getCount() == 0) {
                db.calendarEventDao().insertAll(CalendarSeedData.getAll());
            }
        });
    }
}

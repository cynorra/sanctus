package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import java.util.ArrayList;
import java.util.List;

public class PrayerTimeCalculator {
    // Catholic Canonical Hours (Liturgy of the Hours)
    public static List<PrayerTime> getCanonicalHours(Context context) {
        List<PrayerTime> hours = new ArrayList<>();
        hours.add(new PrayerTime("lauds", context.getString(R.string.hour_lauds), "06:00 AM", true));
        hours.add(new PrayerTime("terce", context.getString(R.string.hour_terce), "09:00 AM", false));
        hours.add(new PrayerTime("sext", context.getString(R.string.hour_sext), "12:00 PM", false));
        hours.add(new PrayerTime("none", context.getString(R.string.hour_none), "03:00 PM", false));
        hours.add(new PrayerTime("vespers", context.getString(R.string.hour_vespers), "06:00 PM", true));
        hours.add(new PrayerTime("compline", context.getString(R.string.hour_compline), "09:00 PM", false));
        return hours;
    }

    public static String getNextHour(Context context) {
        // Simplified: return vespers as default "next" for demo
        return context.getString(R.string.next_hour_value);
    }
}

package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrayerTimeCalculator {
    // Catholic Canonical Hours (Liturgy of the Hours): {hour, minute}
    private static final int[][] HOUR_TIMES = {{6, 0}, {9, 0}, {12, 0}, {15, 0}, {18, 0}, {21, 0}};

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

    /** Returns "<Hour name> · <time>" for the next canonical hour still ahead today, or tomorrow's Lauds if past Compline. */
    public static String getNextHour(Context context) {
        String[] shortNames = context.getResources().getStringArray(R.array.hour_names_short);
        int i = getNextHourIndex();
        return formatHour(context, shortNames[i], HOUR_TIMES[i][0], HOUR_TIMES[i][1]);
    }

    /** Index into {@link #getCanonicalHours} / HOUR_TIMES of the next hour still ahead today, or 0 (Lauds) if past Compline. */
    public static int getNextHourIndex() {
        Calendar now = Calendar.getInstance();
        int nowMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE);
        for (int i = 0; i < HOUR_TIMES.length; i++) {
            int hourMinutes = HOUR_TIMES[i][0] * 60 + HOUR_TIMES[i][1];
            if (hourMinutes > nowMinutes) return i;
        }
        return 0;
    }

    private static String formatHour(Context context, String name, int hour, int minute) {
        return name + " · " + DateUtils.getTimeFormatted(hour, minute);
    }
}

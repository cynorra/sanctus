package com.sanctus.christian.prayer.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static String getTodayFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault());
        return sdf.format(new Date());
    }

    public static String getTimeFormatted(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(cal.getTime());
    }

    public static String getDayOfWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        return sdf.format(new Date());
    }

    public static int getDayOfWeekInt() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}

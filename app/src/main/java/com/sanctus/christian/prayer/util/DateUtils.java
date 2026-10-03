package com.sanctus.christian.prayer.util;

import com.sanctus.christian.prayer.SanctusApplication;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /**
     * The in-app language, not the device's. Locale.getDefault() follows the system language, so
     * an English UI on a Turkish phone rendered weekday/month names in Turkish.
     */
    public static Locale appLocale() {
        try {
            return new Locale(SanctusApplication.getInstance().getSettingsRepository().getLanguage());
        } catch (RuntimeException e) {
            return Locale.getDefault();
        }
    }

    public static String getTodayFormatted() {
        return new SimpleDateFormat("EEEE, MMMM d", appLocale()).format(new Date());
    }

    public static String getTimeFormatted(int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return new SimpleDateFormat("hh:mm a", appLocale()).format(cal.getTime());
    }

    public static String getDayOfWeek() {
        return new SimpleDateFormat("EEEE", appLocale()).format(new Date());
    }

    public static int getDayOfWeekInt() {
        return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    }
}

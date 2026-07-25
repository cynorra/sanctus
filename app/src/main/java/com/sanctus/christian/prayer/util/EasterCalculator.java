package com.sanctus.christian.prayer.util;

import java.util.Calendar;

/**
 * Computes the Gregorian-calendar date of Easter Sunday for a given year
 * using the Anonymous Gregorian algorithm (Meeus/Jones/Butcher).
 * All other movable feasts are expressed as a fixed day offset from Easter.
 */
public class EasterCalculator {

    /** Returns a Calendar (midnight, local time) set to Easter Sunday for the given year. */
    public static Calendar getEasterSunday(int year) {
        int a = year % 19;
        int b = year / 100;
        int c = year % 100;
        int d = b / 4;
        int e = b % 4;
        int f = (b + 8) / 25;
        int g = (b - f + 1) / 3;
        int h = (19 * a + b - d - g + 15) % 30;
        int i = c / 4;
        int k = c % 4;
        int l = (32 + 2 * e + 2 * i - h - k) % 7;
        int m = (a + 11 * h + 22 * l) / 451;
        int month = (h + l - 7 * m + 114) / 31; // 3 = March, 4 = April
        int day = ((h + l - 7 * m + 114) % 31) + 1;

        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day, 0, 0, 0);
        return cal;
    }

    /** Returns the Gregorian date for a movable feast, `offsetDays` days from Easter Sunday of `year`. */
    public static Calendar getDateForOffset(int year, int offsetDays) {
        Calendar cal = getEasterSunday(year);
        cal.add(Calendar.DAY_OF_YEAR, offsetDays);
        return cal;
    }

    /** Formats a Calendar as "MM-dd" for comparison against fixed-date events. */
    public static String toMonthDay(Calendar cal) {
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format(java.util.Locale.ROOT, "%02d-%02d", month, day);
    }
}

package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;
import java.util.Calendar;

public class LiturgicalSeasonHelper {
    public enum Season { ADVENT, CHRISTMAS, ORDINARY, LENT, EASTER }

    /** Season for the user's tradition: Orthodox follows the Julian calendar, everyone else the Gregorian one. */
    public static Season getCurrentSeason(String tradition) {
        if (Tradition.ORTHODOX.equals(tradition)) return OrthodoxCalendar.season(Calendar.getInstance());
        return getCurrentSeason();
    }

    public static Season getCurrentSeason() {
        Calendar today = Calendar.getInstance();
        clearTime(today);
        int year = today.get(Calendar.YEAR);

        Calendar easter = EasterCalculator.getEasterSunday(year);
        Calendar ashWednesday = addDays(easter, -46);
        Calendar pentecost = addDays(easter, 49);
        Calendar advent1 = adventStart(year);
        Calendar christmas = dateOf(year, Calendar.DECEMBER, 25);
        Calendar baptismOfLord = baptismOfTheLord(year);

        if (!today.before(ashWednesday) && today.before(easter)) return Season.LENT;
        if (!today.before(easter) && !today.after(pentecost)) return Season.EASTER;
        if (!today.before(advent1) && today.before(christmas)) return Season.ADVENT;
        if (!today.before(christmas)) return Season.CHRISTMAS;
        if (today.get(Calendar.MONTH) == Calendar.JANUARY && !today.after(baptismOfLord)) return Season.CHRISTMAS;

        return Season.ORDINARY;
    }

    /** Fourth Sunday before Christmas (the Sunday on/before Dec 24, minus 21 days). */
    private static Calendar adventStart(int year) {
        Calendar dec24 = dateOf(year, Calendar.DECEMBER, 24);
        int daysBackToSunday = dec24.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
        Calendar fourthAdventSunday = addDays(dec24, -daysBackToSunday);
        return addDays(fourthAdventSunday, -21);
    }

    /** Sunday after Epiphany (Jan 6); if Jan 6 itself is Sunday, the following Monday. */
    private static Calendar baptismOfTheLord(int year) {
        Calendar epiphany = dateOf(year, Calendar.JANUARY, 6);
        int dow = epiphany.get(Calendar.DAY_OF_WEEK);
        if (dow == Calendar.SUNDAY) return addDays(epiphany, 1);
        int daysUntilSunday = (Calendar.SUNDAY - dow + 7) % 7;
        if (daysUntilSunday == 0) daysUntilSunday = 7;
        return addDays(epiphany, daysUntilSunday);
    }

    private static Calendar dateOf(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, month, day, 0, 0, 0);
        return c;
    }

    private static Calendar addDays(Calendar base, int days) {
        Calendar c = (Calendar) base.clone();
        c.add(Calendar.DAY_OF_YEAR, days);
        return c;
    }

    private static void clearTime(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    public static String getSeasonColor(Season season) {
        switch (season) {
            case ADVENT: return "#4A148C";   // Purple
            case LENT: return "#4A148C";     // Purple
            case CHRISTMAS: return "#F5F5DC"; // White/Gold
            case EASTER: return "#FFFFFF";    // White
            default: return "#2E7D32";        // Green (Ordinary)
        }
    }

    public static String getSeasonName(Context context, Season season) {
        switch (season) {
            case ADVENT: return context.getString(R.string.season_advent);
            case CHRISTMAS: return context.getString(R.string.season_christmas);
            case LENT: return context.getString(R.string.season_lent);
            case EASTER: return context.getString(R.string.season_easter);
            default: return context.getString(R.string.season_ordinary);
        }
    }
}

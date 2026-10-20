package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import com.sanctus.christian.prayer.domain.model.ResolvedCalendarEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * The Orthodox (Julian) church calendar, expressed in Gregorian dates. Pascha follows the Julian
 * computus, fixed feasts sit on their Julian date, so each is shifted by the current gap between
 * the two calendars (13 days from 1900 to 2099).
 */
public final class OrthodoxCalendar {

    private OrthodoxCalendar() {
    }

    /** Fixed feasts: Julian month (1-12), Julian day, title, great feast (true) or lesser feast. */
    private static final Object[][] FIXED = {
            {1, 1, R.string.orth_circumcision, false},
            {1, 6, R.string.orth_theophany, true},
            {1, 7, R.string.orth_john_baptist_synaxis, false},
            {2, 2, R.string.orth_meeting, true},
            {3, 25, R.string.orth_annunciation, true},
            {4, 23, R.string.orth_george, false},
            {6, 24, R.string.orth_nativity_john, true},
            {6, 29, R.string.orth_peter_paul, true},
            {8, 6, R.string.orth_transfiguration, true},
            {8, 15, R.string.orth_dormition, true},
            {8, 29, R.string.orth_beheading, false},
            {9, 8, R.string.orth_nativity_theotokos, true},
            {9, 14, R.string.orth_exaltation, true},
            {10, 1, R.string.orth_protection, false},
            {11, 8, R.string.orth_archangels, false},
            {11, 21, R.string.orth_entry_theotokos, true},
            {12, 6, R.string.orth_nicholas, false},
            {12, 25, R.string.orth_nativity, true},
    };

    /** Movable feasts: offset from Pascha in days. */
    private static final Object[][] MOVABLE = {
            {-48, R.string.orth_clean_monday, false},
            {-42, R.string.orth_orthodoxy_sunday, false},
            {-7, R.string.orth_palm_sunday, true},
            {-2, R.string.orth_great_friday, true},
            {-1, R.string.orth_great_saturday, true},
            {0, R.string.orth_pascha, true},
            {7, R.string.orth_thomas_sunday, false},
            {39, R.string.orth_ascension, true},
            {49, R.string.orth_pentecost, true},
            {56, R.string.orth_all_saints, false},
    };

    /** Days the Julian calendar trails the Gregorian one in {@code year}. */
    static int julianGap(int year) {
        return year / 100 - year / 400 - 2;
    }

    /** Orthodox Easter Sunday as a Gregorian date (Meeus Julian algorithm). */
    public static Calendar pascha(int year) {
        int a = year % 4;
        int b = year % 7;
        int c = year % 19;
        int d = (19 * c + 15) % 30;
        int e = (2 * a + 4 * b - d + 34) % 7;
        int month = (d + e + 114) / 31;
        int day = ((d + e + 114) % 31) + 1;
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, month - 1, day + julianGap(year), 0, 0, 0);
        return cal;
    }

    private static Calendar fixed(int year, int julianMonth, int julianDay) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(year, julianMonth - 1, julianDay + julianGap(year), 0, 0, 0);
        return cal;
    }

    /** Every feast that falls in the given Gregorian year. */
    public static List<ResolvedCalendarEvent> forYear(Context context, int year) {
        List<ResolvedCalendarEvent> out = new ArrayList<>();
        int id = -year * 100;
        for (Object[] f : FIXED) {
            Calendar day = fixed(year, (int) f[0], (int) f[1]);
            // Julian Dec 25 lands on Jan 7 of the following Gregorian year, so use the previous year's.
            if (day.get(Calendar.YEAR) != year) day = fixed(year - 1, (int) f[0], (int) f[1]);
            out.add(resolved(context, id--, (int) f[2], (boolean) f[3], day));
        }
        Calendar pascha = pascha(year);
        for (Object[] m : MOVABLE) {
            Calendar day = (Calendar) pascha.clone();
            day.add(Calendar.DAY_OF_YEAR, (int) m[0]);
            out.add(resolved(context, id--, (int) m[1], (boolean) m[2], day));
        }
        return out;
    }

    private static ResolvedCalendarEvent resolved(Context context, int id, int titleRes, boolean great, Calendar day) {
        CalendarEventEntity ev = new CalendarEventEntity();
        ev.id = id;
        ev.title = context.getString(titleRes);
        ev.isFeastDay = true;
        ev.isSolemnity = great;
        ev.isHolyDayOfObligation = great;
        ev.season = "ORDINARY";
        ev.color = "white";
        ev.description = "";
        return new ResolvedCalendarEvent(ev, day);
    }

    public static List<ResolvedCalendarEvent> today(Context context, Calendar today) {
        List<ResolvedCalendarEvent> out = new ArrayList<>();
        for (ResolvedCalendarEvent r : forYear(context, today.get(Calendar.YEAR))) {
            if (r.occursOn.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) out.add(r);
        }
        return out;
    }

    /** The next {@code limit} feasts from today onward, rolling into next year. */
    public static List<ResolvedCalendarEvent> upcoming(Context context, Calendar today, int limit) {
        Calendar start = (Calendar) today.clone();
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);
        start.set(Calendar.MILLISECOND, 0);
        List<ResolvedCalendarEvent> all = new ArrayList<>();
        int year = today.get(Calendar.YEAR);
        for (int y = year; y <= year + 1; y++) {
            for (ResolvedCalendarEvent r : forYear(context, y)) {
                if (!r.occursOn.before(start)) all.add(r);
            }
        }
        Collections.sort(all, (a, b) -> a.occursOn.compareTo(b.occursOn));
        return all.size() > limit ? all.subList(0, limit) : all;
    }

    /** Season used by the header chip when the Orthodox calendar is active. */
    public static LiturgicalSeasonHelper.Season season(Calendar today) {
        Calendar day = (Calendar) today.clone();
        day.set(Calendar.HOUR_OF_DAY, 0);
        day.set(Calendar.MINUTE, 0);
        day.set(Calendar.SECOND, 0);
        day.set(Calendar.MILLISECOND, 0);
        int year = day.get(Calendar.YEAR);
        Calendar pascha = pascha(year);
        Calendar lentStart = (Calendar) pascha.clone();
        lentStart.add(Calendar.DAY_OF_YEAR, -48);
        Calendar pentecost = (Calendar) pascha.clone();
        pentecost.add(Calendar.DAY_OF_YEAR, 49);
        Calendar nativityFast = fixed(year, 11, 15);
        Calendar nativity = fixed(year - 1, 12, 25);   // Jan 7 of this year
        Calendar theophany = fixed(year, 1, 6);        // Jan 19 of this year

        if (!day.before(lentStart) && day.before(pascha)) return LiturgicalSeasonHelper.Season.LENT;
        if (!day.before(pascha) && !day.after(pentecost)) return LiturgicalSeasonHelper.Season.EASTER;
        if (!day.before(nativityFast) || day.before(nativity)) return LiturgicalSeasonHelper.Season.ADVENT;
        if (!day.after(theophany)) return LiturgicalSeasonHelper.Season.CHRISTMAS;
        return LiturgicalSeasonHelper.Season.ORDINARY;
    }
}

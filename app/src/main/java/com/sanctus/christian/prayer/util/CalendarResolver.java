package com.sanctus.christian.prayer.util;

import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import com.sanctus.christian.prayer.domain.model.ResolvedCalendarEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/** Resolves CalendarEventEntity rows (fixed-date or Easter-relative) to actual occurrence dates for a given year. */
public class CalendarResolver {

    public static Calendar resolveOccurrence(CalendarEventEntity ev, int year) {
        if (ev.easterOffset != null) {
            return EasterCalculator.getDateForOffset(year, ev.easterOffset);
        }
        String[] parts = ev.date.split("-");
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(year, Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1]), 0, 0, 0);
        return c;
    }

    private static boolean isSameDay(Calendar a, Calendar b) {
        return a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
                && a.get(Calendar.DAY_OF_YEAR) == b.get(Calendar.DAY_OF_YEAR);
    }

    public static List<ResolvedCalendarEvent> resolveForToday(List<CalendarEventEntity> events, Calendar today) {
        List<ResolvedCalendarEvent> result = new ArrayList<>();
        int year = today.get(Calendar.YEAR);
        for (CalendarEventEntity ev : events) {
            Calendar occurs = resolveOccurrence(ev, year);
            if (isSameDay(occurs, today)) result.add(new ResolvedCalendarEvent(ev, occurs));
        }
        return result;
    }

    /** Solemnities/holy days from today onward, sorted ascending, rolled into next year once this year's date has passed. */
    public static List<ResolvedCalendarEvent> resolveUpcomingMajorFeasts(List<CalendarEventEntity> events, Calendar today, int limit) {
        List<ResolvedCalendarEvent> result = new ArrayList<>();
        int year = today.get(Calendar.YEAR);
        for (CalendarEventEntity ev : events) {
            if (!ev.isSolemnity && !ev.isHolyDayOfObligation) continue;
            Calendar occurs = resolveOccurrence(ev, year);
            if (occurs.before(today)) occurs = resolveOccurrence(ev, year + 1);
            result.add(new ResolvedCalendarEvent(ev, occurs));
        }
        Collections.sort(result, (a, b) -> a.occursOn.compareTo(b.occursOn));
        return result.size() > limit ? result.subList(0, limit) : result;
    }
}

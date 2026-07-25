package com.sanctus.christian.prayer.domain.model;

import com.sanctus.christian.prayer.data.local.entity.CalendarEventEntity;
import java.util.Calendar;

/** A CalendarEventEntity paired with its actual occurrence date for the year being displayed. */
public class ResolvedCalendarEvent {
    public final CalendarEventEntity event;
    public final Calendar occursOn;

    public ResolvedCalendarEvent(CalendarEventEntity event, Calendar occursOn) {
        this.event = event;
        this.occursOn = occursOn;
    }
}

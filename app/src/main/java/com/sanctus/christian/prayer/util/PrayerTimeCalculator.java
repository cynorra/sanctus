package com.sanctus.christian.prayer.util;

import com.sanctus.christian.prayer.domain.model.PrayerTime;
import java.util.ArrayList;
import java.util.List;

public class PrayerTimeCalculator {
    // Catholic Canonical Hours (Liturgy of the Hours)
    public static List<PrayerTime> getCanonicalHours() {
        List<PrayerTime> hours = new ArrayList<>();
        hours.add(new PrayerTime("lauds", "Lauds (Morning Prayer)", "06:00 AM", true));
        hours.add(new PrayerTime("terce", "Terce (Mid-Morning)", "09:00 AM", false));
        hours.add(new PrayerTime("sext", "Sext (Midday Prayer)", "12:00 PM", false));
        hours.add(new PrayerTime("none", "None (Mid-Afternoon)", "03:00 PM", false));
        hours.add(new PrayerTime("vespers", "Vespers (Evening Prayer)", "06:00 PM", true));
        hours.add(new PrayerTime("compline", "Compline (Night Prayer)", "09:00 PM", false));
        return hours;
    }

    public static String getNextHour() {
        // Simplified: return vespers as default "next" for demo
        return "Vespers · 06:00 PM";
    }
}

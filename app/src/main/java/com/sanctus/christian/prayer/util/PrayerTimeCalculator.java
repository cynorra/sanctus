package com.sanctus.christian.prayer.util;

import android.content.Context;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.domain.model.PrayerTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PrayerTimeCalculator {
    private static final String[] IDS = {"lauds", "terce", "sext", "none", "vespers", "compline"};
    private static final int[] NAME_RES = {
        R.string.hour_lauds, R.string.hour_terce, R.string.hour_sext,
        R.string.hour_none, R.string.hour_vespers, R.string.hour_compline};

    private static SettingsRepository settings() {
        return SanctusApplication.getInstance().getSettingsRepository();
    }

    /** The six canonical hours with the user's own times and reminder switches. */
    public static List<PrayerTime> getCanonicalHours(Context context) {
        SettingsRepository s = settings();
        List<PrayerTime> hours = new ArrayList<>();
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) {
            int m = s.getHourMinutes(i);
            hours.add(new PrayerTime(IDS[i], context.getString(NAME_RES[i]),
                    DateUtils.getTimeFormatted(m / 60, m % 60), s.isHourEnabled(i)));
        }
        return hours;
    }

    /** Returns "<Hour name> · <time>" for the next hour still ahead today, or tomorrow's first one. */
    public static String getNextHour(Context context) {
        String[] shortNames = context.getResources().getStringArray(R.array.hour_names_short);
        int i = getNextHourIndex();
        int m = settings().getHourMinutes(i);
        return shortNames[i] + " · " + DateUtils.getTimeFormatted(m / 60, m % 60);
    }

    /**
     * Index of the next hour with its reminder on that is still ahead today; if none is left today,
     * the earliest one tomorrow. Falls back to all hours when every reminder is switched off.
     */
    public static int getNextHourIndex() {
        SettingsRepository s = settings();
        boolean anyOn = false;
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) anyOn |= s.isHourEnabled(i);

        Calendar now = Calendar.getInstance();
        int nowMinutes = now.get(Calendar.HOUR_OF_DAY) * 60 + now.get(Calendar.MINUTE);
        int earliest = -1;
        int next = -1;
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) {
            if (anyOn && !s.isHourEnabled(i)) continue;
            int m = s.getHourMinutes(i);
            if (earliest < 0 || m < s.getHourMinutes(earliest)) earliest = i;
            if (m > nowMinutes && (next < 0 || m < s.getHourMinutes(next))) next = i;
        }
        return next >= 0 ? next : Math.max(earliest, 0);
    }
}

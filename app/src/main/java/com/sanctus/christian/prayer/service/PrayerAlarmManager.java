package com.sanctus.christian.prayer.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.receiver.PrayerNotificationReceiver;
import java.util.Calendar;

public class PrayerAlarmManager {

    // Canonical Hours schedule: hour, minute, id, name
    private static final int[][] CANONICAL_HOURS = {
        {6,  0,  1001, 0},  // Lauds     (Morning Prayer)
        {9,  0,  1002, 1},  // Terce
        {12, 0,  1003, 2},  // Sext      (Midday)
        {15, 0,  1004, 3},  // None
        {18, 0,  1005, 4},  // Vespers   (Evening Prayer)
        {21, 0,  1006, 5},  // Compline  (Night Prayer)
    };

    public static void scheduleAll(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;

        String[] hourNames = context.getResources().getStringArray(R.array.hour_names_short);
        for (int[] h : CANONICAL_HOURS) {
            int hour = h[0], minute = h[1], reqCode = h[2], nameIdx = h[3];
            scheduleHour(context, am, hour, minute, reqCode, hourNames[nameIdx]);
        }
    }

    private static void scheduleHour(Context context, AlarmManager am,
                                     int hour, int minute, int reqCode, String name) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0);
        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        Intent intent = new Intent(context, PrayerNotificationReceiver.class);
        intent.putExtra("hour_name", name);
        intent.putExtra("req_code", reqCode);

        PendingIntent pi = PendingIntent.getBroadcast(context, reqCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (am.canScheduleExactAlarms()) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            } else {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
            }
        } else {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        }
    }

    public static void cancelAll(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;
        for (int[] h : CANONICAL_HOURS) {
            Intent intent = new Intent(context, PrayerNotificationReceiver.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, h[2], intent,
                    PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
            if (pi != null) am.cancel(pi);
        }
    }
}

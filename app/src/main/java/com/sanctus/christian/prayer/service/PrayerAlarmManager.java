package com.sanctus.christian.prayer.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.receiver.PrayerNotificationReceiver;
import java.util.Calendar;

/**
 * Schedules the canonical-hour reminders. Each hour is a one-shot alarm for its next occurrence
 * (the receiver re-arms the next day), at the time the user picked, only if its switch is on.
 */
public class PrayerAlarmManager {

    public static final int REQUEST_BASE = 1001;
    public static final String EXTRA_HOUR_INDEX = "hour_index";

    /** Re-arms every enabled hour and cancels the disabled ones. Safe to call any time. */
    public static void scheduleAll(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;
        SettingsRepository settings = SanctusApplication.getInstance().getSettingsRepository();
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) {
            if (settings.isHourEnabled(i)) {
                schedule(context, am, i, settings.getHourMinutes(i));
            } else {
                cancel(context, am, i);
            }
        }
    }

    private static PendingIntent pendingIntent(Context context, int index, int flags) {
        Intent intent = new Intent(context, PrayerNotificationReceiver.class);
        intent.putExtra(EXTRA_HOUR_INDEX, index);
        return PendingIntent.getBroadcast(context, REQUEST_BASE + index, intent,
                flags | PendingIntent.FLAG_IMMUTABLE);
    }

    private static void schedule(Context context, AlarmManager am, int index, int minutesOfDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, minutesOfDay / 60);
        cal.set(Calendar.MINUTE, minutesOfDay % 60);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (!cal.after(Calendar.getInstance())) cal.add(Calendar.DAY_OF_YEAR, 1);

        PendingIntent pi = pendingIntent(context, index, PendingIntent.FLAG_UPDATE_CURRENT);
        if (canScheduleExact(am)) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        } else {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        }
    }

    /** False on Android 12+ until the user grants "Alarms & reminders"; reminders then drift by minutes. */
    public static boolean canScheduleExact(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        return am != null && canScheduleExact(am);
    }

    private static boolean canScheduleExact(AlarmManager am) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.S || am.canScheduleExactAlarms();
    }

    private static void cancel(Context context, AlarmManager am, int index) {
        PendingIntent pi = pendingIntent(context, index, PendingIntent.FLAG_NO_CREATE);
        if (pi != null) {
            am.cancel(pi);
            pi.cancel();
        }
    }

    public static void cancelAll(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (am == null) return;
        for (int i = 0; i < SettingsRepository.HOUR_COUNT; i++) cancel(context, am, i);
    }
}

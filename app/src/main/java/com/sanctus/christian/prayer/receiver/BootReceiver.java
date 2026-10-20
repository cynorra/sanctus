package com.sanctus.christian.prayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Alarms are wiped by reboot, app update and clock/zone changes (and the exact-alarm grant
        // changes how they're set), so every one of those re-arms them.
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)
                || "android.intent.action.QUICKBOOT_POWERON".equals(action)
                || Intent.ACTION_MY_PACKAGE_REPLACED.equals(action)
                || Intent.ACTION_TIMEZONE_CHANGED.equals(action)
                || Intent.ACTION_TIME_CHANGED.equals(action)
                || "android.app.action.SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED".equals(action)) {
            if (SanctusApplication.getInstance() != null &&
                SanctusApplication.getInstance().getSettingsRepository().isNotificationsEnabled()) {
                PrayerAlarmManager.scheduleAll(context);
            }
        }
    }
}

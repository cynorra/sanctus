package com.sanctus.christian.prayer.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.widget.PrayerWidgetProvider;

public class PrayerNotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "prayer_times";

    @Override
    public void onReceive(Context context, Intent intent) {
        String hourName = intent.getStringExtra("hour_name");
        int reqCode = intent.getIntExtra("req_code", 1001);
        int idx = reqCode - 1001;

        String[] subtitles = context.getResources().getStringArray(R.array.hour_notification_subtitles);
        if (idx < 0 || idx >= subtitles.length) idx = 0;
        String subtitle = subtitles[idx];

        Intent openIntent = new Intent(context, MainActivity.class);
        openIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(context, reqCode, openIntent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cross)
                .setContentTitle(hourName != null ? hourName : context.getString(R.string.notification_default_title))
                .setContentText(subtitle)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(subtitle))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) nm.notify(reqCode, builder.build());

        // Re-schedule for next day
        com.sanctus.christian.prayer.service.PrayerAlarmManager.scheduleAll(context);
        PrayerWidgetProvider.refreshAll(context);
    }
}

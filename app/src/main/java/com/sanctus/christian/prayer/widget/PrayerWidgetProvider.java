package com.sanctus.christian.prayer.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.util.PrayerTimeCalculator;

public class PrayerWidgetProvider extends AppWidgetProvider {

    public static void refreshAll(Context context) {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new android.content.ComponentName(context, PrayerWidgetProvider.class));
        if (ids.length > 0) {
            new PrayerWidgetProvider().onUpdate(context, manager, ids);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_prayer);

            views.setTextViewText(R.id.widget_hour_value, PrayerTimeCalculator.getNextHour(context));

            int streak = SanctusApplication.getInstance().getSettingsRepository().getStreak();
            views.setTextViewText(R.id.widget_streak,
                    streak > 0 ? context.getString(R.string.streak_days_format, streak) : "");

            Intent openApp = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context, 0, openApp,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent);

            appWidgetManager.updateAppWidget(id, views);
        }
    }
}

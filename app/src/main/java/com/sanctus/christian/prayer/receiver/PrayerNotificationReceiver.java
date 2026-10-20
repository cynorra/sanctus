package com.sanctus.christian.prayer.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.SanctusApplication;
import com.sanctus.christian.prayer.data.local.SanctusDatabase;
import com.sanctus.christian.prayer.data.local.entity.PrayerEntity;
import com.sanctus.christian.prayer.data.local.entity.SaintEntity;
import com.sanctus.christian.prayer.data.repository.SettingsRepository;
import com.sanctus.christian.prayer.service.PrayerAlarmManager;
import com.sanctus.christian.prayer.ui.main.MainActivity;
import com.sanctus.christian.prayer.util.LanguageManager;
import com.sanctus.christian.prayer.util.Tradition;
import com.sanctus.christian.prayer.widget.PrayerWidgetProvider;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

/**
 * Fires the reminder for one canonical hour. The text names a real prayer for that hour (and, at
 * Lauds, the saint of the day) and tapping it opens that prayer, so the notification is a reason
 * to open the app rather than a bare "time to pray".
 */
public class PrayerNotificationReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "prayer_times";

    // Which prayer category suits each hour, ordered Lauds..Compline.
    private static final String[] HOUR_CATEGORY = {
        "morning", "devotional", "scripture", "devotional", "evening", "evening"};

    @Override
    public void onReceive(Context context, Intent intent) {
        final Context app = context.getApplicationContext();
        final int index = Math.max(0, Math.min(SettingsRepository.HOUR_COUNT - 1,
                intent.getIntExtra(PrayerAlarmManager.EXTRA_HOUR_INDEX, 0)));
        final PendingResult result = goAsync();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                SettingsRepository settings = SanctusApplication.getInstance().getSettingsRepository();
                // Re-arm tomorrow first so a failure below never silences later reminders.
                if (settings.isNotificationsEnabled()) PrayerAlarmManager.scheduleAll(app);
                if (settings.isNotificationsEnabled() && settings.isHourEnabled(index)) {
                    post(app, settings, index);
                }
                PrayerWidgetProvider.refreshAll(app);
            } finally {
                result.finish();
            }
        });
    }

    private void post(Context appContext, SettingsRepository settings, int index) {
        String language = settings.getLanguage();
        // A receiver's context follows the device language; the text must follow the in-app one.
        Context context = LanguageManager.applyLanguage(appContext, language);
        String tradition = Tradition.filterValue(settings.getTradition());
        SanctusDatabase db = SanctusDatabase.getInstance(context);

        PrayerEntity prayer = pickPrayer(db, index, language, tradition);
        SaintEntity saint = index == 0 ? pickSaint(db, language, tradition) : null;

        String[] titles = {
            context.getString(R.string.hour_lauds), context.getString(R.string.hour_terce),
            context.getString(R.string.hour_sext), context.getString(R.string.hour_none),
            context.getString(R.string.hour_vespers), context.getString(R.string.hour_compline)};
        String[] subtitles = context.getResources().getStringArray(R.array.hour_notification_subtitles);

        StringBuilder body = new StringBuilder(subtitles[index]);
        if (saint != null) {
            body.append('\n').append(context.getString(R.string.notification_saint_today, saint.name));
        }
        if (prayer != null) {
            body.append('\n').append(context.getString(R.string.notification_pray_today, prayer.name));
        }

        Intent open = new Intent(context, MainActivity.class);
        open.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (prayer != null) {
            open.putExtra(MainActivity.EXTRA_DEEP_LINK_PRAYER_NAME, prayer.name);
            open.putExtra(MainActivity.EXTRA_DEEP_LINK_PRAYER_LANGUAGE, prayer.language);
        }
        int reqCode = PrayerAlarmManager.REQUEST_BASE + index;
        PendingIntent pi = PendingIntent.getActivity(context, reqCode, open,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cross)
                .setContentTitle(titles[index])
                .setContentText(body.toString().replace('\n', ' '))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body.toString()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setAutoCancel(true)
                .setContentIntent(pi);

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm != null) nm.notify(reqCode, builder.build());
    }

    /** A different prayer each day for the hour's category, in the user's language (English as fallback). */
    private static PrayerEntity pickPrayer(SanctusDatabase db, int index, String language, String tradition) {
        String category = HOUR_CATEGORY[index];
        List<PrayerEntity> list = db.prayerDao().listByCategory(category, language, tradition);
        if (list.isEmpty() && !"en".equals(language)) list = db.prayerDao().listByCategory(category, "en", tradition);
        if (list.isEmpty()) return null;
        int day = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return list.get((day + index) % list.size());
    }

    private static SaintEntity pickSaint(SanctusDatabase db, String language, String tradition) {
        String today = new SimpleDateFormat("MMMM d", Locale.ENGLISH).format(new Date());
        SaintEntity saint = db.saintDao().findForDate(today, language, tradition);
        if (saint == null && !"en".equals(language)) saint = db.saintDao().findForDate(today, "en", tradition);
        return saint;
    }
}

package com.sanctus.christian.prayer.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sanctus.christian.prayer.R;
import com.sanctus.christian.prayer.ui.main.MainActivity;

public class SanctusFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "SanctusFCM";
    public static final String FCM_CHANNEL_ID = "fcm_push_notifications";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(TAG, "Refreshed FCM Token: " + token);
        // Token can be sent to custom backend server if needed in future
    }

    /**
     * FCM behaves very differently depending on the message shape: a payload with a top-level
     * "notification" field is displayed directly by the OS system tray whenever the app is in
     * the background or killed — this method never even runs for it, so any custom deep-link
     * extras we'd want to attach to the tap action are silently dropped. Only a data-only
     * payload (no "notification" field, just "data") reliably reaches this method regardless of
     * app state, which is required for the tap to open the specific prayer instead of just the
     * app's default screen. scripts/send-daily-engagement-notification.js sends data-only for
     * exactly this reason — don't add a "notification" field back without re-checking this.
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String title = null;
        String body = null;

        // Check if message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            title = remoteMessage.getNotification().getTitle();
            body = remoteMessage.getNotification().getBody();
        }

        // Check if message contains data payload. Deep-link fields (prayer_name/language) only
        // ever arrive this way — see the important note on onMessageReceived's doc comment about
        // why the daily engagement push is sent as data-only, never with a top-level "notification".
        String prayerName = null, language = null, saintName = null, saintFeast = null;
        if (remoteMessage.getData().size() > 0) {
            if (title == null && remoteMessage.getData().containsKey("title")) {
                title = remoteMessage.getData().get("title");
            }
            if (body == null && remoteMessage.getData().containsKey("body")) {
                body = remoteMessage.getData().get("body");
            }
            prayerName = remoteMessage.getData().get("prayer_name");
            language = remoteMessage.getData().get("language");
            saintName = remoteMessage.getData().get("saint_name");
            saintFeast = remoteMessage.getData().get("saint_feast_date");
        }

        if (title != null || body != null) {
            sendNotification(title != null ? title : getString(R.string.app_name), body != null ? body : "",
                    prayerName, language, saintName, saintFeast);
        }
    }

    private void sendNotification(String title, String messageBody, String deepLinkPrayerName, String deepLinkLanguage,
                                  String deepLinkSaintName, String deepLinkSaintFeast) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    FCM_CHANNEL_ID,
                    "Announcements & Daily Inspiration",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Push notifications for daily verses, saints, and updates");
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (deepLinkSaintName != null && deepLinkSaintFeast != null && deepLinkLanguage != null) {
            intent.putExtra(MainActivity.EXTRA_DEEP_LINK_SAINT_NAME, deepLinkSaintName);
            intent.putExtra(MainActivity.EXTRA_DEEP_LINK_SAINT_FEAST, deepLinkSaintFeast);
            intent.putExtra(MainActivity.EXTRA_DEEP_LINK_PRAYER_LANGUAGE, deepLinkLanguage);
        } else if (deepLinkPrayerName != null && deepLinkLanguage != null) {
            intent.putExtra(MainActivity.EXTRA_DEEP_LINK_PRAYER_NAME, deepLinkPrayerName);
            intent.putExtra(MainActivity.EXTRA_DEEP_LINK_PRAYER_LANGUAGE, deepLinkLanguage);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                (int) System.currentTimeMillis(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, FCM_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cross)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent);

        notificationManager.notify((int) System.currentTimeMillis(), notificationBuilder.build());
    }
}

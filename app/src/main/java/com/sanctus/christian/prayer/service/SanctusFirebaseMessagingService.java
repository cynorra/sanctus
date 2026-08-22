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

        // Check if message contains data payload
        if (remoteMessage.getData().size() > 0) {
            if (title == null && remoteMessage.getData().containsKey("title")) {
                title = remoteMessage.getData().get("title");
            }
            if (body == null && remoteMessage.getData().containsKey("body")) {
                body = remoteMessage.getData().get("body");
            }
        }

        if (title != null || body != null) {
            sendNotification(title != null ? title : getString(R.string.app_name), body != null ? body : "");
        }
    }

    private void sendNotification(String title, String messageBody) {
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

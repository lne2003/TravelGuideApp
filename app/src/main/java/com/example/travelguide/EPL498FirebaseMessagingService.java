package com.example.travelguide;  // Make sure to use your package name here

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * This class is responsible for handling Firebase Cloud Messages (FCM).
 */
public class EPL498FirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "EPL498FirebaseMessagingService";
    private static final String CHANNEL_ID = "default_channel";  // Notification channel ID

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if the message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            // Handle data payload here if needed (you can perform background tasks or logic)
        }

        // Check if the message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            // Show the notification
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            showNotification(title, message);
        }
    }

    /**
     * Creates and shows a push notification when a new FCM message is received.
     *
     * @param title The title of the notification.
     * @param message The message body of the notification.
     */
    private void showNotification(String title, String message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // For Android 8.0 and above, create a notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_notification)  // Replace with your app's notification icon
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build();

        notificationManager.notify(0, notification);
    }

    /**
     * This method is called when a new FCM token is generated or refreshed.
     * It could happen when:
     * - The app is installed on a new device.
     * - The user clears app data.
     * - The token is updated for any other reason.
     *
     * Use the token to send messages to specific devices.
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "New FCM Token: " + token);

        // Optionally, send this token to your server so you can use it to send notifications
        // Example: sendTokenToServer(token);
    }
}

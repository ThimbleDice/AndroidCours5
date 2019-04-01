package com.cours5.android.androidcours5.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.cours5.android.androidcours5.MainActivity;
import com.cours5.android.androidcours5.R;
import com.cours5.android.androidcours5.notification.model.messageModel;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class NotificationCreator {
    public static Notification createNotificationFromMessage(Context context, messageModel message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "42")
                .setSmallIcon(R.drawable.ssbu)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    public static Notification createNotificationFromImportantMessage(Context context, messageModel message){
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent mainActivityPendingIntent =
                PendingIntent.getBroadcast(context, 0, mainActivityIntent, 0);

        NotificationCompat.Builder builder = new
                NotificationCompat.Builder(context, "11")
                .setSmallIcon(R.drawable.ssbu)
                .setContentTitle(message.getSender())
                .setContentText(message.getMessage())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.ssbu, "SomeRndTitle", PendingIntent.getActivity(context, 0, mainActivityIntent, 0));

        return builder.build();
    }
}
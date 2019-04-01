package com.cours5.android.androidcours5.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.cours5.android.androidcours5.notification.model.messageModel;

import com.cours5.android.androidcours5.MainActivity;
import com.cours5.android.androidcours5.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class NotificationService extends Service{

    public static final String CHANNEL_ID = "NotificationService";
    NotificationManager notificationManager;
    FirebaseFirestore database;
    int idNotification = 2;

    @Override
    public void onCreate() {
        createNotificationChannel();
        database = FirebaseFirestore.getInstance();
        listenForNotificationMessage();
        listenForNotificationImportantMessage();
        super.onCreate();
    }

    public void createNotificationChannel(){
        createNotificationChannelService();
        createNotificationChannelMessage();
        createNotificationChannelImportantMessage();
    }

    private void createNotificationChannelService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = CHANNEL_ID;
            CharSequence channelName = "NotificationService";
            String channelDescription = "Notification service";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannelMessage(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "42";
            CharSequence channelName = "NotificationMessage";
            String channelDescription = "Notification message";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void createNotificationChannelImportantMessage(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String channelId = "11";
            CharSequence channelName = "NotificationImportantMessage";
            String channelDescription = "Notification important message";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, channelName, channelImportance);
            channel.setDescription(channelDescription);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationServiceIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationServiceIntent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ssbu)
                .setContentTitle("Notification service")
                .setContentIntent(pendingIntent);
        Notification notification = builder.build();
        startForeground(1, notification);
        //todo ligne du listen
        return START_STICKY;
    }

    private void listenForNotificationMessage(){
        database.collection("Notification").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentMessage : queryDocumentSnapshots){
                    messageModel message = documentMessage.toObject(messageModel.class);
                    sendNotificationForMessage(message);
                }
            }
        });
    }

    private void sendNotificationForMessage(messageModel message){
        Notification notification = NotificationCreator.createNotificationFromMessage(this, message);
        notificationManager.notify(idNotification, notification);
        idNotification++;
    }

    private void listenForNotificationImportantMessage(){
        database.collection("NotificationImportant").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                for(QueryDocumentSnapshot documentMessage : queryDocumentSnapshots){
                    messageModel message = documentMessage.toObject(messageModel.class);
                    sendNotificationForImportantMessage(message);
                }
            }
        });
    }

    private void sendNotificationForImportantMessage(messageModel message){
        Notification notification = NotificationCreator.createNotificationFromImportantMessage(this, message);
        notificationManager.notify(idNotification, notification);
        idNotification++;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

package com.efficacious.e_smartdeliveryboy.Notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.efficacious.e_smartdeliveryboy.Activity.MainActivity;
import com.efficacious.e_smartdeliveryboy.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class NotificationService extends FirebaseMessagingService {
    String title,message;
    public static String CHANNEL_ID = "Notification_Channel";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getData().get("title");
        message = remoteMessage.getData().get("msg");
        ShowNotification();
    }

    private void ShowNotification() {
        createNotificationChannel();

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setSound(RingtoneManager.getDefaultUri(R.raw.notification_sound))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(404,builder.build());

        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification_sound);
        mediaPlayer.start();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "my notification";
            String Description = "General Notification";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(Description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.setShowBadge(true);
            notificationChannel.canShowBadge();
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification_sound);
            mediaPlayer.start();
        }
    }

}
package com.brots.music.application.firebaseMs;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.brots.music.application.R;
import com.brots.music.application.activity.notifications.Notification_activity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "TAG";
    private NotificationManager mNotificationManager;
    private static long[] vibratePattern = {500, 500, 500, 500, 500, 500, 500, 500, 500};
    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        System.out.print(remoteMessage);
        sendNotification("Brost Test", remoteMessage.getData());

    }

    private void sendNotification(String brost_test, Map<String, String> data) {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), Notification_activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Brots App");
        bigText.setBigContentTitle(data.get("messageType"));
        bigText.setSummaryText(data.get("message"));
        mBuilder.setSound(defaultSoundUri);
        mBuilder.setVibrate(vibratePattern);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.logo);
        mBuilder.setContentTitle(data.get("messageType"));
        mBuilder.setContentText(data.get("message"));
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "YOUR_CHANNEL_ID";
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            mNotificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//        }

        mNotificationManager.notify(0, mBuilder.build());
    }






}
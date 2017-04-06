package com.shuan.Project.push;

import android.app.LauncherActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.shuan.Project.R;

/**
 * Created by Android on 4/1/2017.
 */

public class FcmMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title = remoteMessage.getNotification().getTitle();
        String msg = remoteMessage.getNotification().getBody();
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        int color = 0xa63f07;

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this,LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(msg);
        notificationBuilder.setSmallIcon(R.drawable.ic_stat_logo);
        notificationBuilder.setLargeIcon(largeIcon);
        notificationBuilder.setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setSound(soundUri);
        notificationBuilder.setColor(color);
        // notificationBuilder.setLargeIcon(bmpFinal);
        //notificationBuilder.setLargeIcon();
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationBuilder.build());
    }

}

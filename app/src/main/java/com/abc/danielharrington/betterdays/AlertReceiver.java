package com.abc.danielharrington.betterdays;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_1_ID;
import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_2_ID;


public class AlertReceiver extends BroadcastReceiver {

    private NotificationManagerCompat notificationManager;
    private Context theContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = NotificationManagerCompat.from(context);
        theContext = context;
        sendOnChannel1();
    }//onReceive method

    public void sendOnChannel1(){
        String title = "Better Days";
        String message = "New Quote Available";

        Notification notification = new NotificationCompat.Builder(theContext, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title).setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }//sendOnChannel1 method

    public void sendOnChannel2(){
        String title = "Better Days";
        String message = "New Quote Available";

        Notification notification = new NotificationCompat.Builder(theContext, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(2, notification);

    }//sendOnChannel2 method
}//AlertReceiver method

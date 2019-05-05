package com.abc.danielharrington.betterdays;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BetterDays extends Application {

    public static final String CHANNEL_1_ID = "quotes notification";
    public static final String CHANNEL_2_ID = "quotes notification 2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID, "New Quote!", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setDescription("New quotes notification");

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID, "New Quote!", NotificationManager.IMPORTANCE_DEFAULT);
            channel2.enableLights(true);
            channel2.enableVibration(true);
            channel2.setDescription("New quotes notification");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }

    }//createNotificationChannels method
}

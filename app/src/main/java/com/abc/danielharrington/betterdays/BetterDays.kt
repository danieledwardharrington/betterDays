package com.abc.danielharrington.betterdays

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class BetterDays : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(CHANNEL_1_ID, "New Quote!", NotificationManager.IMPORTANCE_DEFAULT)
            channel1.enableLights(true)
            channel1.enableVibration(true)
            channel1.description = "New quotes notification"

            val channel2 = NotificationChannel(CHANNEL_2_ID, "New Quote!", NotificationManager.IMPORTANCE_DEFAULT)
            channel2.enableLights(true)
            channel2.enableVibration(true)
            channel2.description = "New quotes notification"

            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }

    }//createNotificationChannels method

    companion object {

        val CHANNEL_1_ID = "quotes notification"
        val CHANNEL_2_ID = "quotes notification 2"
    }
}
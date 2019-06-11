package com.abc.danielharrington.betterdays

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class MyJobIntentService: JobIntentService() {

    var TAG: String = ""
    val ctx = MainActivity.getApplicationContext()
    private var notificationManager: NotificationManagerCompat? = null

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork")
        notificationManager = NotificationManagerCompat.from(ctx)

        AlertReceiver.setAlarms(ctx)
        sendOnChannel1()
        stopSelf()
    }//onHandleWork method

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }//onCreate method

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }//onDestroy method

    override fun onStopCurrentWork(): Boolean {
        Log.d(TAG, "onStopCurrentWork")
        return super.onStopCurrentWork()
    }//onStopCurrentWork method

    private fun sendOnChannel1() {
        val title = "New Quote Available"
        val message = "Come check it out!"

        var index: Int = 0
        if(MainActivity.quotesList.size != 0) {
            index = Random.nextInt(MainActivity.quotesList.size)
        }//if
        QuotesFragment.quoteText = MainActivity.quotesList[index]
        QuotesFragment.speakerText = MainActivity.speakersList[index]

        QuotesFragment.quoteTextView?.text = MainActivity.quotesList[index]
        QuotesFragment.speakerTextView?.text = MainActivity.speakersList[index]


        val intent = Intent(ctx, MainActivity::class.java)
        intent.putExtra("From", "quotesFragment")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(ctx, BetterDays.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build()

        val id = createID()

        notificationManager!!.notify(id, notification)

    }//sendOnChannel1 method

    //method to generate a unique ID
    private fun createID(): Int{
        val now = Date()
        val id = Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.US).format(now))
        return id
    }//createID method


    companion object {
        val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, MyJobIntentService::class.java, JOB_ID, work)
        }//enqueueWork method

    }//companion
}//MyJobIntentService class
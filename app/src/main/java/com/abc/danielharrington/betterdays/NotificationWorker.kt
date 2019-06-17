package com.abc.danielharrington.betterdays
/*
WorkManager to deal with periodic, daily notifications sent to the user
 */


import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class NotificationWorker(appContext: Context, workerParams: WorkerParameters): Worker(appContext, workerParams) {

    private var notificationManager: NotificationManagerCompat? = null

    override fun doWork(): Result {

        notificationManager = NotificationManagerCompat.from(applicationContext)

        sendOnChannel1(applicationContext)
        return Result.success()
    }//doWork method

    private fun sendOnChannel1(theContext: Context) {
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

        val sharedPreferences = theContext.getSharedPreferences(SettingsFragment.SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(SettingsFragment.SAVED_QUOTE, QuotesFragment.quoteText)
        editor.putString(SettingsFragment.SAVED_SPEAKER, QuotesFragment.speakerText)

        editor.apply()


        val intent = Intent(theContext, MainActivity::class.java)
        intent.putExtra("From", "quotesFragment")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(theContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(theContext, BetterDays.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()

        val id = createID()

        notificationManager!!.notify(id, notification)

    }//sendOnChannel1 method

/*    //for future functionality
    fun sendOnChannel2() {
        val title = "Title"
        val message = "Message"

        val notification = NotificationCompat.Builder(theContext!!, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

        notificationManager!!.notify(2, notification)

    }//sendOnChannel2 method*/

    //method to generate a unique ID
    private fun createID(): Int{
        val now = Date()
        val id = Integer.parseInt(SimpleDateFormat("ddHHmmss", Locale.US).format(now))
        return id
    }//createID method

}//NotificationWorker class
package com.abc.danielharrington.betterdays

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

import com.abc.danielharrington.betterdays.BetterDays.Companion.CHANNEL_1_ID
import com.abc.danielharrington.betterdays.BetterDays.Companion.CHANNEL_2_ID
import com.abc.danielharrington.betterdays.MainActivity.Companion.quotesList
import com.abc.danielharrington.betterdays.MainActivity.Companion.speakersList
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteTextView
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerTextView
import kotlin.random.Random


class AlertReceiver : BroadcastReceiver() {

    private var notificationManager: NotificationManagerCompat? = null
    private var theContext: Context? = null

    override fun onReceive(context: Context, intent: Intent) {

        notificationManager = NotificationManagerCompat.from(context)
        theContext = context
        sendOnChannel1()
    }//onReceive method

    fun sendOnChannel1() {
        val title = "Better Days"
        val message = "New Quote Available"

        var index: Int = (Random.nextInt() % quotesList.size) + 1

        quoteTextView?.text = quotesList.get(index)
        speakerTextView?.text = speakersList.get(index)


        var intent: Intent = Intent(theContext!!, MainActivity::class.java)
        intent.putExtra("From", "quotesFragment")

        var pendingIntent: PendingIntent = PendingIntent.getActivity(theContext, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(theContext!!, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pendingIntent)
                .build()

        notificationManager!!.notify(1, notification)

    }//sendOnChannel1 method

    fun sendOnChannel2() {
        val title = "Better Days"
        val message = "New Quote Available"

        val notification = NotificationCompat.Builder(theContext!!, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()

        notificationManager!!.notify(2, notification)

    }//sendOnChannel2 method
}//AlertReceiver method

package com.abc.danielharrington.betterdays

/*
Two channel notifications are implemented here but I'm currently
only using one. I might use channel 2 at some point in the future
if I add additional functionality. Good to have it here.
 */

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abc.danielharrington.betterdays.BetterDays.Companion.CHANNEL_1_ID
//import com.abc.danielharrington.betterdays.BetterDays.Companion.CHANNEL_2_ID
import com.abc.danielharrington.betterdays.MainActivity.Companion.quotesList
import com.abc.danielharrington.betterdays.MainActivity.Companion.speakersList
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteTextView
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerTextView
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SAVED_QUOTE
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SAVED_SPEAKER
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SHARED_PREFS
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class AlertReceiver : BroadcastReceiver() {

    private var notificationManager: NotificationManagerCompat? = null
    private var theContext: Context? = null

    override fun onReceive(context: Context, intent: Intent) {

        notificationManager = NotificationManagerCompat.from(context)
        theContext = context
        sendOnChannel1()
    }//onReceive method

    private fun sendOnChannel1() {
        val title = "New Quote Available"
        val message = "Come check it out!"

        var index: Int = 0
        if(quotesList.size != 0) {
            index = Random.nextInt(quotesList.size)
        }//if
        quoteText = quotesList[index]
        speakerText = speakersList[index]

        quoteTextView?.text = quotesList[index]
        speakerTextView?.text = speakersList[index]

        val sharedPreferences = theContext!!.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(SAVED_QUOTE, quoteText)
        editor.putString(SAVED_SPEAKER, speakerText)

        editor.apply()


        val intent = Intent(theContext!!, MainActivity::class.java)
        intent.putExtra("From", "quotesFragment")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(theContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(theContext!!, CHANNEL_1_ID)
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
}//AlertReceiver class

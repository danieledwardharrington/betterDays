package com.abc.danielharrington.betterdays

/*
Two channel notifications are implemented here but I'm currently
only using one. I might use channel 2 at some point in the future
if I add additional functionality. Good to have it here.
 */

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abc.danielharrington.betterdays.MainActivity.Companion.quotesList
import com.abc.danielharrington.betterdays.MainActivity.Companion.speakersList
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteTextView
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerTextView
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SAVED_QUOTE
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SAVED_SPEAKER
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SHARED_PREFS
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class AlertReceiver : BroadcastReceiver() {

    private var notificationManager: NotificationManagerCompat? = null
    private var theContext: Context? = null
    val CHANNEL_1_ID = "quotes notification"
    //val CHANNEL_2_ID = "quotes notification 2"

    override fun onReceive(context: Context, intent: Intent) {

        notificationManager = NotificationManagerCompat.from(context)
        theContext = context
        sendOnChannel1()
    }//onReceive method

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel1 = NotificationChannel(CHANNEL_1_ID, "Quote Channel", NotificationManager.IMPORTANCE_DEFAULT).apply { description = "New quotes notification" }
            channel1.enableLights(true)
            channel1.enableVibration(true)
            //channel1.description = "New quotes notification"

/*            val channel2 = NotificationChannel(CHANNEL_2_ID, "New Quote!", NotificationManager.IMPORTANCE_DEFAULT)
            channel2.enableLights(true)
            channel2.enableVibration(true)
            channel2.description = "New quotes notification" */

            val manager = theContext!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            //manager.createNotificationChannel(channel2)
        }

    }//createNotificationChannels method

    private fun sendOnChannel1() {
        val title = "New Quote Available"
        val message = "Come check it out!"

        loadLists()

        var index: Int = 0
        if(quotesList.size != 0) {
            index = Random.nextInt(quotesList.size)
            quoteText = quotesList[index]
            speakerText = speakersList[index]

            quoteTextView?.text = quotesList[index]
            speakerTextView?.text = speakersList[index]
        }//if
        else if (quotesList.size == 0){

        }//else if

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
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .build()

        val id = createID()

        createNotificationChannels()

        notificationManager!!.notify(id, notification)

    }//sendOnChannel1 method

    private fun loadLists(){

        val sharedPreferences = theContext!!.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val gson = Gson()
        val gson2 = Gson()

        val quotesJson: String? = sharedPreferences.getString(SettingsFragment.QUOTES_PREF, "")
        val type: Type = object : TypeToken<ArrayList<String>>(){}.type
        quotesList = gson.fromJson(quotesJson, type)

        val speakersJson: String? = sharedPreferences.getString(SettingsFragment.SPEAKERS_PREF, "")
        val type2: Type = object : TypeToken<ArrayList<String>>(){}.type
        speakersList = gson2.fromJson(speakersJson, type2)

    }//loadLists method

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

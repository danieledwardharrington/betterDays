package com.abc.danielharrington.betterdays

/*
Two channel notifications are implemented here but I'm currently
only using one. I might use channel 2 at some point in the future
if I add additional functionality. Good to have it here.
 */

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.abc.danielharrington.betterdays.BetterDays.Companion.CHANNEL_1_ID
import com.abc.danielharrington.betterdays.MainActivity.Companion.quotesList
import com.abc.danielharrington.betterdays.MainActivity.Companion.speakersList
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.quoteTextView
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerText
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.speakerTextView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random


class AlertReceiver : BroadcastReceiver() {

    private var notificationManager: NotificationManagerCompat? = null
    var ctx: Context? = null

    override fun onReceive(context: Context, intent: Intent) {

        //notificationManager = NotificationManagerCompat.from(context)
        //sendOnChannel1()
        this.ctx = context

        MyJobIntentService.enqueueWork(context, intent)
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


        val intent = Intent(ctx, MainActivity::class.java)
        intent.putExtra("From", "quotesFragment")

        val pendingIntent: PendingIntent = PendingIntent.getActivity(ctx, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notification = NotificationCompat.Builder(ctx!!, CHANNEL_1_ID)
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

/*    //for future functionality
    fun sendOnChannel2() {
        val title = "Title"
        val message = "Message"

        val notification = NotificationCompat.Builder(ctx, CHANNEL_2_ID)
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


    companion object {

        //method to set repeating notification alarms (random times)
        fun setAlarms(ctx: Context) {
            //clearing any previously saved alarms to prevent tons of extra
            clearAlarms(ctx)
            SettingsFragment.calList.clear()


            var hour: Int
            var minute: Int

            for (i in 0 until (SettingsFragment.NOTIFICATIONS_PER_DAY)) {
                val hourRand = (0..23).random()
                val minuteRand = (0..59).random()

                hour = hourRand
                minute = minuteRand
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                cal.set(Calendar.SECOND, 0)

                SettingsFragment.calList.add(cal)
            }//for

            var i = 0
            for (cal in SettingsFragment.calList) {
                val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(ctx, AlertReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(ctx, i, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
                println(i)
                i++
            }//for
        }//setAlarms method

        //method to clear the alarms
        fun clearAlarms(ctx: Context) {

            var i = 0
            for (cal in SettingsFragment.calList) {
                val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                val intent = Intent(ctx, AlertReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(ctx, i, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                alarmManager.cancel(pendingIntent)
                i++
            }//for

        }//clearAlarms method

    }//companion



}//AlertReceiver class

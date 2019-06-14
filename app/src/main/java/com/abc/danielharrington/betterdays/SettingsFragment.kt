package com.abc.danielharrington.betterdays
/*
A good deal of the heavy lifting is being done in this fragment
alongside the main activity.
 */


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

import java.util.ArrayList
import java.util.Calendar
import java.util.Random

import android.content.Context.MODE_PRIVATE
import com.abc.danielharrington.betterdays.MainActivity.Companion.quotesList
import com.abc.danielharrington.betterdays.MainActivity.Companion.speakersList
import com.google.gson.Gson


class SettingsFragment : PreferenceFragmentCompat() {

    private var themePreference: ListPreference? = null
    private var notificationsPreference: ListPreference? = null
    private var savePreference: Preference? = null

    //private var NOTIFICATIONS_PER_DAY: Int = 0

    //private var calList: ArrayList<Calendar> = ArrayList()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        themePreference = findPreference("theme_preference")
        notificationsPreference = findPreference("notifications_preference")
        savePreference = findPreference("save_preference_button")

        calList = ArrayList()

        //handling the theme preference
        themePreference!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            THEME_SELECTED = newValue.toString() //setting the selection to the constant for saving
            true
        }

        //handling the notifications preference
        notificationsPreference!!.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, newValue ->
            NOTIFICATIONS_PER_DAY = Integer.parseInt(newValue.toString()) //setting the selection to the constant for saving
            true
        }

        //handling the save
        savePreference!!.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            saveData()
            true
        }

    }//onCreatePreferences

    //method to save preferences when the user clicks "SAVE"
    private fun saveData() {

        if (NOTIFICATIONS_PER_DAY > 0) {
            setAlarms()
        } else {
            clearAlarms() //clearing if the user is removing notifications
        }

        val sharedPreferences = activity!!.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(THEME_PREF, THEME_SELECTED)
        editor.putInt(NOTS_PREF, NOTIFICATIONS_PER_DAY)

        editor.apply()

        Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
    }//saveData method

    //method to set repeating notification alarms (random times)
    private fun setAlarms() {
        //clearing any previously saved alarms to prevent tons of extra
        clearAlarms()
        calList.clear()


        var hour: Int
        var minute: Int

        for (i in 0 until (NOTIFICATIONS_PER_DAY)) {
            val hourRand = (0..23).random()
            val minuteRand = (0..59).random()

            hour = hourRand
            minute = minuteRand
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)

            calList.add(cal)
        }//for

        var i = 0
        for (cal in calList) {
            val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlertReceiver::class.java)
            intent.setClass(context!!, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0)

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
            println(i)
            i++
        }//for

    }//setAlarms method

    //method to clear the alarms
    private fun clearAlarms() {

        var i = 0
        for (cal in calList) {
            val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0)

            alarmManager.cancel(pendingIntent)
            i++
        }//for

    }//clearAlarms method

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setBackgroundResource(R.color.signBlue)
        activity!!.title = "Settings"
    }//onViewCreated method

    companion object {

        const val SHARED_PREFS = "sharedPrefs"
        const val THEME_PREF = "themePreference"
        const val NOTS_PREF = "notificationsPreference"
        const val QUOTES_PREF = "quotesListPreference"
        const val SPEAKERS_PREF = "speakersListPreference"
        const val SAVED_QUOTE = "savedQuote"
        const val SAVED_SPEAKER = "savedSpeaker"
        var THEME_SELECTED: String? = null
        var NOTIFICATIONS_PER_DAY: Int = 0
        var calList: ArrayList<Calendar> = ArrayList()
    }//companion object
}//SettingsFragment class
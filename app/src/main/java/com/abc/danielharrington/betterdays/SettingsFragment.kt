package com.abc.danielharrington.betterdays
/*
A good deal of the heavy lifting is being done in this fragment
alongside the main activity.
 */


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.util.ArrayList
import java.util.Calendar
import android.content.Context.MODE_PRIVATE
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


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
            startWork() //starting periodic WorkManager
        } else {
            cancelWork() //cancelling the work if the user doesn't want notifications anymore
        }//if/else

        val sharedPreferences = activity!!.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString(THEME_PREF, THEME_SELECTED)
        editor.putInt(NOTS_PREF, NOTIFICATIONS_PER_DAY)

        editor.apply()

        Toast.makeText(context, "Settings saved", Toast.LENGTH_SHORT).show()
    }//saveData method

    private fun startWork(){
        //cancel any work that already exists
        cancelWork()

        var delayTime: Long

        for(i in 0 until NOTIFICATIONS_PER_DAY){
            delayTime = (0..23).random().toLong() //to mimic a "random" time, not taking minutes into account

            val task = PeriodicWorkRequest.Builder(
                   NotificationWorker::class.java,
                    1,
                    TimeUnit.DAYS,
                    30,
                    TimeUnit.MINUTES)
                    .addTag("periodic_quote_not")
                    .setInitialDelay(delayTime, TimeUnit.HOURS)
                    .build()
            println("Delay time: $delayTime")
            WorkManager.getInstance(context!!).enqueueUniquePeriodicWork("periodic_quote_work", ExistingPeriodicWorkPolicy.REPLACE, task)

        }//for

    }//startWork method

    private fun cancelWork(){
        WorkManager.getInstance(context!!).cancelAllWorkByTag("periodic_quote_not") //used one tag for all
    }//cancelWork method

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
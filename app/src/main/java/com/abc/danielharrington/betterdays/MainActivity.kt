package com.abc.danielharrington.betterdays

/*
Author: Daniel Harrington

Purpose: Simple app that sets random notifications (up to 6) per
day and generates a random inspirational quote (taken from a txt file).
Intended to be a lightweight, simple, approachable mental health app
geared toward improving day to day well-being and being a companion to
other forms of therapy and treatments (as well as other apps).

Last Edited: June 3, 2019
 */

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.QUOTES_THEME
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.NOTS_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.QUOTES_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.THEME_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SHARED_PREFS
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SPEAKERS_PREF
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawer: DrawerLayout? = null
    private var quotesFragment: QuotesFragment? = null
    private var aboutFragment: AboutFragment? = null
    private var settingsFragment: SettingsFragment? = null
    private var welcomeFragment: WelcomeFragment? = null

    companion object{
        var quotesList: ArrayList<String> = ArrayList()
        var speakersList: ArrayList<String> = ArrayList()
        var appNotifications: Int = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aboutFragment = AboutFragment()
        settingsFragment = SettingsFragment()
        welcomeFragment = WelcomeFragment()

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, welcomeFragment!!).commit()
            navView.setCheckedItem(R.id.nav_quotes)
        }//if

        //if the list doesn't have anything to load, then populate
        if(quotesList.size == 0 || speakersList.size == 0) {
            //populating the quotes and speakers lists
            populateLists()
        }//if

        //load saved data (preferences and lists)
        loadData()

        quotesFragment = QuotesFragment()

        val quoteFrag: String = intent.getStringExtra("From") ?: ""
        val fromSettingsQuotes: String = intent.getStringExtra("FromQuotes") ?: ""
        val fromSettingsAbout: String = intent.getStringExtra("FromAbout") ?: ""

        if(quoteFrag == "quotesFragment"){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
        }//if

        if(fromSettingsQuotes == "quotesFragment"){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
        }//if

        if(fromSettingsAbout == "aboutFragment"){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment!!).commit()
        }//if

    }//onCreate method

    //overriding so I can set alarms again when the app is killed
    override fun onDestroy() {
        super.onDestroy()
        setAlarms()
    }//onDestroy

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_quotes -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment!!).commit()
            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }//settings
            R.id.nav_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val shareBody = "Better Days: A Wellness Companion App"
                val shareSubject = "Check out this app!"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(shareIntent, "Share using"))
            }//share
        }//when

        drawer!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer!!.isDrawerOpen(GravityCompat.START)) {
            drawer!!.closeDrawer(GravityCompat.START)

        } else {
            super.onBackPressed()
        }
    }//onBackPressed

    //method to load the saved data
    private fun loadData() {

        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val gson = Gson()
        val gson2 = Gson()
        QUOTES_THEME = sharedPreferences.getString(THEME_PREF, "")
        appNotifications = sharedPreferences.getInt(NOTS_PREF, 0)

        val quotesJson: String? = sharedPreferences.getString(QUOTES_PREF, "")
        val type: Type = object : TypeToken<ArrayList<String>>(){}.type
        quotesList = gson.fromJson(quotesJson, type)

        val speakersJson: String? = sharedPreferences.getString(SPEAKERS_PREF, "")
        val type2: Type = object : TypeToken<ArrayList<String>>(){}.type
        speakersList = gson2.fromJson(speakersJson, type2)
    }//loadData method

    //method to populate the lists
    private fun populateLists(){
        //clearing them to avoid repeats
        quotesList.clear()
        speakersList.clear()

        var lineFromFile: String

        try {
            applicationContext.assets.open("quotesTextFile.txt").bufferedReader().forEachLine {
                lineFromFile = it

                if (lineFromFile.contains("\"")) {
                    quotesList.add(lineFromFile)
                }//if
                else {
                    speakersList.add(lineFromFile)
                }//else

            }//foreach

            val gson = Gson()
            val quotesJson: String = gson.toJson(quotesList)

            val gson2 = Gson()
            val speakersJson: String = gson2.toJson(speakersList)

            val sharedPreferences = this.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(QUOTES_PREF, quotesJson)
            editor.putString(SPEAKERS_PREF, speakersJson)

            editor.apply()
        } catch(e: Exception){
            createToast("Error getting quotes!")
        }//catch

    }//populateLists method

    //easy method for toasts
    private fun createToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }//createToast method

    //these methods are copies of those found in the settings fragment, this is so
    //they could be called in onDestroy to so notifications get fired when app is
    //force closed

    //method to set repeating notification alarms (random times)
    private fun setAlarms() {
        //clearing any previously saved alarms to prevent tons of extra
        clearAlarms()
        SettingsFragment.calList.clear()

        val rand = Random()
        var hour: Int
        var minute: Int

        for (i in 0 until (SettingsFragment.NOTIFICATIONS_PER_DAY)) {
            hour = rand.nextInt(25)
            minute = rand.nextInt(61)
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            cal.set(Calendar.SECOND, 0)

            SettingsFragment.calList.add(cal)
        }//for

        var i = 0
        for (cal in SettingsFragment.calList) {
            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, i)

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
            println(i)
            i++
        }//for

    }//setAlarms method

    //method to clear the alarms
    private fun clearAlarms() {

        var i = 0
        for (cal in SettingsFragment.calList) {
            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlertReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, i)

            alarmManager.cancel(pendingIntent)
            i++
        }//for

    }//clearAlarms method


}//MainActivity class

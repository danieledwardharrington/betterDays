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
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawer: DrawerLayout? = null
    private var quotesFragment: QuotesFragment? = null
    private var aboutFragment: AboutFragment? = null
    private var settingsFragment: SettingsFragment? = null
    private var welcomeFragment: WelcomeFragment? = null

    init {
        instance = this
    }

    companion object{
        var quotesList: ArrayList<String> = ArrayList()
        var speakersList: ArrayList<String> = ArrayList()
        var appNotifications: Int = 0

        private var instance: MainActivity? = null

        fun getApplicationContext(): Context{
            return instance!!.applicationContext
        }
    }//companion

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


}//MainActivity class

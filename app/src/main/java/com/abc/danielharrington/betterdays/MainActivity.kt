package com.abc.danielharrington.betterdays

import android.content.Context
import android.content.Intent
import com.google.android.material.navigation.NavigationView

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import android.content.SharedPreferences
import android.content.res.AssetManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager

import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.Toast
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.QUOTES_THEME
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.NOTS_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.THEME_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SHARED_PREFS
import java.io.File
import java.io.InputStream


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawer: DrawerLayout? = null
    private var quotesFragment: QuotesFragment? = null
    private var aboutFragment: AboutFragment? = null
    private var settingsFragment: SettingsFragment? = null

    private var appNotifications: Int = 0

    companion object{
        var quotesList: ArrayList<String> = ArrayList()
        var speakersList: ArrayList<String> = ArrayList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //populating the quotes and speakers lists
        populateLists()

        /*
        //for launching a fragment from notification
        var type: String = getIntent().getStringExtra("From")
        if(type != null){
            when (type){
                "quotesFragment" -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
                }//quotesFragment
            }//when
        }//if
        */

        aboutFragment = AboutFragment()
        settingsFragment = SettingsFragment()

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
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment!!).commit()
            navView.setCheckedItem(R.id.nav_quotes)
        }//if

        loadData()
        quotesFragment = QuotesFragment()

        var quoteFrag: String = intent.getStringExtra("From") ?: ""

        if(quoteFrag == "quotesFragment"){
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
        }//if

    }//onCreate method

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_quotes -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment!!).commit()
            R.id.nav_settings -> {
                val intent: Intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_share -> {
            }
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
    fun loadData() {

        val sharedPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        QUOTES_THEME = sharedPreferences.getString(THEME_PREF, "")
        appNotifications = sharedPreferences.getInt(NOTS_PREF, 0)
    }//loadData method

    //method to populate the lists
    private fun populateLists(){
        //clearing them to avoid repeats
        quotesList.clear()
        speakersList.clear()

        var lineFromFile: String = ""

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
        } catch(e: Exception){
            createToast("Error getting quotes!")
        }//catch

    }//populateLists method

    //easy method for toasts
    private fun createToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }//createToast method


}//MainActivity class

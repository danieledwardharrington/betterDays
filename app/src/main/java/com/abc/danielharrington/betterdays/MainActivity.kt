package com.abc.danielharrington.betterdays

import android.content.Context
import com.google.android.material.navigation.NavigationView

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager

import android.view.MenuItem
import android.widget.RelativeLayout
import com.abc.danielharrington.betterdays.QuotesFragment.Companion.QUOTES_THEME
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.NOTS_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.THEME_PREF
import com.abc.danielharrington.betterdays.SettingsFragment.Companion.SHARED_PREFS


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawer: DrawerLayout? = null
    private var quotesFragment: QuotesFragment? = null
    private var aboutFragment: AboutFragment? = null
    private var settingsFragment: SettingsFragment? = null

    private var appNotifications: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    }//onCreate method

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_quotes -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, quotesFragment!!).commit()
            R.id.nav_about -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, aboutFragment!!).commit()
            R.id.nav_settings -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, settingsFragment!!).commit()
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
        val context = applicationContext

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        QUOTES_THEME = sharedPreferences.getString(THEME_PREF, "Beach")
        appNotifications = sharedPreferences.getInt(NOTS_PREF, 0)
    }//loadData method


}//MainActivity class

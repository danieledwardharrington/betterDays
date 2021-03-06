package com.abc.danielharrington.betterdays

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class SettingsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var drawer: DrawerLayout? = null
    private var quotesFragment: QuotesFragment? = null
    private var aboutFragment: AboutFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        quotesFragment = QuotesFragment()
        aboutFragment = AboutFragment()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer = findViewById(R.id.drawer_layout)

        val toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        val navView = findViewById<NavigationView>(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit();
    }//onCreate method

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        //sending them back to the main activity so the bg updates without
        //having to kill the app and open it again
        when (item.itemId) {
            R.id.nav_quotes -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("FromQuotes", "quotesFragment")
                startActivity(intent)
            }//quotes
            R.id.nav_about -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("FromAbout", "aboutFragment")
                startActivity(intent)
            }//about
            R.id.nav_settings -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SettingsFragment()).commit()
            R.id.nav_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val shareBody = "Better Days: A Wellness Companion App"
                val shareSubject = "Check out this app!"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(shareIntent, "Share using"))
            }//nav_share
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

}//SettingsActivity class
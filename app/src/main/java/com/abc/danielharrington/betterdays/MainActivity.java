package com.abc.danielharrington.betterdays;

import android.app.Notification;
import android.content.Intent;

import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_1_ID;
import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_2_ID;
import static com.abc.danielharrington.betterdays.SettingsFragment.NOTS_PREF;
import static com.abc.danielharrington.betterdays.SettingsFragment.SHARED_PREFS;
import static com.abc.danielharrington.betterdays.SettingsFragment.THEME_PREF;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NotificationManagerCompat notificationManager;
    private QuotesFragment quotesFragment;
    private AboutFragment aboutFragment;

    private String appTheme;
    private RelativeLayout quotesBackgroundLayout;
    private int appNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quotesFragment = new QuotesFragment();
        aboutFragment = new AboutFragment();

        quotesBackgroundLayout = findViewById(R.id.quotes_background_layout);

        notificationManager = NotificationManagerCompat.from(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new QuotesFragment()).commit();
            navView.setCheckedItem(R.id.nav_quotes);
        }//if

        loadData();
        updateTheme();


    }//onCreate method

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_quotes:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, quotesFragment).commit();
                break;
            case R.id.nav_about:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, aboutFragment).commit();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_share:

                break;

        }//switch

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }else {
            super.onBackPressed();
        }
    }//onBackPressed


    //a method to update the background theme; called by onCreate
    public void updateTheme(){

        switch(appTheme){
            case "Forest":
                quotesBackgroundLayout.setBackgroundResource(R.drawable.img_forest);
                break;
            case "Beach":
                quotesBackgroundLayout.setBackgroundResource(R.drawable.img_beach);
                break;
            case "Space":
                quotesBackgroundLayout.setBackgroundResource(R.drawable.img_space);
                break;
            case "Mountain":
                quotesBackgroundLayout.setBackgroundResource(R.drawable.img_mountain);
                break;
            case "Default":
                quotesBackgroundLayout.setBackgroundResource(R.color.signBlue);
                break;
            default:
                quotesBackgroundLayout.setBackgroundResource(R.color.signBlue);
                break;
        }//switch

    }//updateTheme method

    //method to load the saved data
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        appTheme = sharedPreferences.getString(THEME_PREF, "Default");
        appNotifications = sharedPreferences.getInt(NOTS_PREF, 0);
    }//loadData method


}//MainActivity class

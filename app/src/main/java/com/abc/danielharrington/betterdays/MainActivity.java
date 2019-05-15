package com.abc.danielharrington.betterdays;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;

import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_1_ID;
import static com.abc.danielharrington.betterdays.BetterDays.CHANNEL_2_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NotificationManagerCompat notificationManager;
    private QuotesFragment quotesFragment;
    private AboutFragment aboutFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        quotesFragment = new QuotesFragment();
        aboutFragment = new AboutFragment();
        settingsFragment = new SettingsFragment();

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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settingsFragment).commit();
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

    public void sendOnChannel1(View view){
        String title = "Better Days";
        String message = "New Quote Available";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title).setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);

    }//sendOnChannel1 method

    public void sendOnChannel2(View view){
        String title = "Better Days";
        String message = "New Quote Available";

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_quotes)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(2, notification);

    }//sendOnChannel2 method


}//MainActivity class

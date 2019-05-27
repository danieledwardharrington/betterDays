package com.abc.danielharrington.betterdays;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;


public class SettingsFragment extends PreferenceFragmentCompat {

    private ListPreference themePreference;
    private ListPreference notificationsPreference;
    private Preference savePreference;

    private String THEME_SELECTED;
    private int NOTIFICATIONS_PER_DAY;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String THEME_PREF = "theme_preference";
    public static final String NOTS_PREF = "notifications_preference";

    private String theme;
    private int notifications;

    ArrayList<Calendar> calList;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);



        themePreference = findPreference("theme_preference");
        notificationsPreference = findPreference("notifications_preference");
        savePreference = findPreference("save_preference_button");

        calList = new ArrayList<>();

        //handling the save
        savePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                saveData();

                return true;
            }
        });

        //handling the theme preference
        themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                THEME_SELECTED = newValue.toString(); //setting the selection to the constant for saving
                return true;
            }
        });

        //handling the notifications preference
        notificationsPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                NOTIFICATIONS_PER_DAY = Integer.parseInt(newValue.toString()); //setting the selection to the constant for saving
                return true;
            }
        });

    }//onCreatePreferences

    //method to save preferences when the user clicks "SAVE"
    public void saveData(){

        if(NOTIFICATIONS_PER_DAY > 0) {
            setAlarms();
        }else{
            clearAlarms();
        }

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(THEME_PREF, THEME_SELECTED);
        editor.putInt(NOTS_PREF, NOTIFICATIONS_PER_DAY);

        editor.apply();

        Toast.makeText(getContext(), "Settings saved", Toast.LENGTH_SHORT).show();
    }//saveData method

    //method to load the saved data
    public void loadData(){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        theme = sharedPreferences.getString(THEME_PREF, "Default");
        notifications = sharedPreferences.getInt(NOTS_PREF, 0);
    }//loadData method

    //method to set repeating notification alarms (random times)
    public void setAlarms(){
        Random rand = new Random();
        int hour;
        int minute;

        for (int i = 0; i < NOTIFICATIONS_PER_DAY; i++){
            hour = rand.nextInt(25);
            minute = rand.nextInt(61);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);

            calList.add(cal);
        }//for

        for(Calendar cal: calList) {
            int i = 0;
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, i);

            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            i++;
        }//for
    }//setAlarms method

    //method to clear the alarms
    public void clearAlarms(){
        for(Calendar cal: calList) {
            int i = 0;
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getContext(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, i);

            alarmManager.cancel(pendingIntent);
            i++;
        }//for

    }//clearAlarms method

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundResource(R.color.signBlue);
    }
}//SettingsFragment class
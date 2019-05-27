package com.abc.danielharrington.betterdays;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.util.ArrayList;
import java.util.Calendar;

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

    int hour;
    int minute;
    ArrayList<Calendar> calList;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

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

    }//setAlarms method

    //method to clear the alarms
    public void clearAlarms(){

    }//clearAlarms method
}//SettingsFragment class
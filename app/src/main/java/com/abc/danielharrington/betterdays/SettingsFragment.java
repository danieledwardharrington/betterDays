package com.abc.danielharrington.betterdays;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Switch notificationSwitch;
    private TextView notificationsTV;
    private TextView perDayTV;
    private Button saveButton;
    private Button clearButton;

    private RelativeLayout quotesLayout;

    ArrayList<Calendar> calList;

    Spinner quotesSpinner;
    Spinner themesSpinner;

    private int QUOTES_SPINNER_VALUE;
    private String THEMES_SPINNER_VALUE;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        getActivity().setTitle("Settings");

        notificationSwitch = view.findViewById(R.id.notification_switch);
        notificationsTV = view.findViewById(R.id.notifications_onoff_textview);
        perDayTV = view.findViewById(R.id.per_day_textview);
        saveButton = view.findViewById(R.id.save_button);
        clearButton = view.findViewById(R.id.clear_button);

        calList = new ArrayList<>();
        quotesLayout = view.findViewById(R.id.quotes_layout);

        quotesSpinner = view.findViewById(R.id.per_day_spinner);
        ArrayAdapter<CharSequence> quotesAdapter = ArrayAdapter.createFromResource(getContext(), R.array.quotes_per_day_numbers, android.R.layout.simple_spinner_item);
        quotesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quotesSpinner.setAdapter(quotesAdapter);
        quotesSpinner.setOnItemSelectedListener(this);

        themesSpinner = view.findViewById(R.id.themes_spinner);
        ArrayAdapter<CharSequence> themeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.bg_themes, android.R.layout.simple_spinner_item);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        themesSpinner.setAdapter(themeAdapter);
        themesSpinner.setOnItemSelectedListener(this);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    perDayTV.setVisibility(View.VISIBLE);
                    quotesSpinner.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                    clearButton.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "Notifications On", Toast.LENGTH_SHORT);
                } else{
                    perDayTV.setVisibility(View.INVISIBLE);
                    quotesSpinner.setVisibility(View.INVISIBLE);
                    saveButton.setVisibility(View.INVISIBLE);
                    clearButton.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "Notifications Cleared", Toast.LENGTH_SHORT);
                }//if/else
            }//onCheckChanged
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuotesBackground();

                int hour;
                int minute;

                //for loop adding calendars to the list
                for (int i = 0; i < QUOTES_SPINNER_VALUE; i++){
                    Calendar cal = Calendar.getInstance();
                    hour = (int) (Math.random() % 24) + 1;
                    minute = (int) (Math.random() % 60) + 1;

                    cal.set(Calendar.HOUR_OF_DAY, hour);
                    cal.set(Calendar.MINUTE, minute);
                    cal.set(Calendar.SECOND, 0);
                    calList.add(cal);
                }//for

                Toast.makeText(getActivity(), "Times Set Randomly", Toast.LENGTH_SHORT).show();
                startAlarm();

            }//onClick
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (Calendar cal : calList) {
                    cancelAlarm();
                }//for

                Toast.makeText(getActivity(), "Notifications Cleared", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }//onCreateView method


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.per_day_spinner) {
            QUOTES_SPINNER_VALUE = Integer.parseInt(quotesSpinner.getSelectedItem().toString());
        }//if
        else if(parent.getId() == R.id.themes_spinner){
            THEMES_SPINNER_VALUE = themesSpinner.getSelectedItem().toString();
        }//else/if
    }//onItemSelected method

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }//onNothingSelected method

    private void startAlarm(){
        for (Calendar cal : calList) {
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), alarmManager.INTERVAL_DAY, pendingIntent);
        }//for
    }//startAlarm method

    private void cancelAlarm(){
            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(getActivity(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);
            alarmManager.cancel(pendingIntent);
    }//cancelAlarm method

    //method to change the quotes fragment background based on user preference
    private void setQuotesBackground(){
        THEMES_SPINNER_VALUE = THEMES_SPINNER_VALUE.toUpperCase();

        switch(THEMES_SPINNER_VALUE){
            case "DEFAULT":
                quotesLayout.setBackgroundColor(getResources().getColor(R.color.signBlue));
                break;
            case "FOREST":
                quotesLayout.setBackground(getResources().getDrawable(R.drawable.img_forest));
                break;
            case "SPACE":
                quotesLayout.setBackground(getResources().getDrawable(R.drawable.img_space));
                break;
            case "MOUNTAIN":
                quotesLayout.setBackground(getResources().getDrawable(R.drawable.img_mountain));
                break;
            case "BEACH":
                quotesLayout.setBackground(getResources().getDrawable(R.drawable.img_beach));
                break;
            default:
                quotesLayout.setBackgroundColor(getResources().getColor(R.color.signBlue));
                break;
        }//switch


    }//setQuotesBackground method


}//SettingsFragment class
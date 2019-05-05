package com.abc.danielharrington.betterdays;

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
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private int QUOTES_PER_DAY;
    private TextView time1tv;
    private TextView time2tv;
    private TextView time3tv;
    private TextView time4tv;
    private TextView time5tv;
    private TextView time6tv;

    private Button time1button;
    private Button time2button;
    private Button time3button;
    private Button time4button;
    private Button time5button;
    private Button time6button;

    private Switch notificationSwitch;
    private TextView notificationsTV;
    private TextView perDayTV;

    Spinner spinner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        getActivity().setTitle("Settings");

        time1tv = view.findViewById(R.id.time_1_textview);
        time2tv = view.findViewById(R.id.time_2_textview);
        time3tv = view.findViewById(R.id.time_3_textview);
        time4tv = view.findViewById(R.id.time_4_textview);
        time5tv = view.findViewById(R.id.time_5_textview);
        time6tv = view.findViewById(R.id.time_6_textview);

        time1button = view.findViewById(R.id.time_1_button);
        time2button = view.findViewById(R.id.time_2_button);
        time3button = view.findViewById(R.id.time_3_button);
        time4button = view.findViewById(R.id.time_4_button);
        time5button = view.findViewById(R.id.time_5_button);
        time6button = view.findViewById(R.id.time_6_button);

        notificationSwitch = view.findViewById(R.id.notification_switch);
        notificationsTV = view.findViewById(R.id.notifications_onoff_textview);
        perDayTV = view.findViewById(R.id.per_day_textview);

        spinner = view.findViewById(R.id.per_day_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.quotes_per_day_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    perDayTV.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.VISIBLE);

                }else{
                    perDayTV.setVisibility(View.INVISIBLE);
                    spinner.setVisibility(View.INVISIBLE);

                    time1tv.setVisibility(View.INVISIBLE);
                    time2tv.setVisibility(View.INVISIBLE);
                    time3tv.setVisibility(View.INVISIBLE);
                    time4tv.setVisibility(View.INVISIBLE);
                    time5tv.setVisibility(View.INVISIBLE);
                    time5tv.setVisibility(View.INVISIBLE);
                    time6tv.setVisibility(View.INVISIBLE);

                    time1button.setVisibility(View.INVISIBLE);
                    time2button.setVisibility(View.INVISIBLE);
                    time3button.setVisibility(View.INVISIBLE);
                    time4button.setVisibility(View.INVISIBLE);
                    time5button.setVisibility(View.INVISIBLE);
                    time6button.setVisibility(View.INVISIBLE);
                }//if/else
            }//onCheckChanged
        });

        return view;
    }//onCreateView method


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString(); //for quotes per day
        QUOTES_PER_DAY = Integer.parseInt(selection);
        String first = "Schedule First Quote";
        String second = "Schedule Second Quote";
        String third = "Schedule Third Quote";
        String fourth = "Schedule Fourth Quote";
        String fifth = "Schedule Fifth Quote";
        String sixth = "Schedule Sixth Quote";

        String button1 = "Time 1";
        String button2 = "Time 2";
        String button3 = "Time 3";
        String button4 = "Time 4";
        String button5 = "Time 5";
        String button6 = "Time 6";

        switch(QUOTES_PER_DAY){
            case 1:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText("");
                time2button.setText("");
                time2button.setVisibility(View.INVISIBLE);


                time3tv.setText("");
                time3button.setText("");
                time3button.setVisibility(View.INVISIBLE);

                time4tv.setText("");
                time4button.setText("");
                time4button.setVisibility(View.INVISIBLE);

                time5tv.setText("");
                time5button.setText("");
                time5button.setVisibility(View.INVISIBLE);

                time6tv.setText("");
                time6button.setText("");
                time6button.setVisibility(View.INVISIBLE);

                break;
            case 2:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText(second);
                time2tv.setVisibility(View.VISIBLE);
                time2button.setText(button2);
                time2button.setVisibility(View.VISIBLE);


                time3tv.setText("");
                time3button.setText("");
                time3button.setVisibility(View.INVISIBLE);

                time4tv.setText("");
                time4button.setText("");
                time4button.setVisibility(View.INVISIBLE);

                time5tv.setText("");
                time5button.setText("");
                time5button.setVisibility(View.INVISIBLE);

                time6tv.setText("");
                time6button.setText("");
                time6button.setVisibility(View.INVISIBLE);

                break;
            case 3:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText(second);
                time2tv.setVisibility(View.VISIBLE);
                time2button.setText(button2);
                time2button.setVisibility(View.VISIBLE);


                time3tv.setText(third);
                time3tv.setVisibility(View.VISIBLE);
                time3button.setText(button3);
                time3button.setVisibility(View.VISIBLE);

                time4tv.setText("");
                time4button.setText("");
                time4button.setVisibility(View.INVISIBLE);

                time5tv.setText("");
                time5button.setText("");
                time5button.setVisibility(View.INVISIBLE);

                time6tv.setText("");
                time6button.setText("");
                time6button.setVisibility(View.INVISIBLE);
                break;
            case 4:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText(second);
                time2tv.setVisibility(View.VISIBLE);
                time2button.setText(button2);
                time2button.setVisibility(View.VISIBLE);


                time3tv.setText(third);
                time3tv.setVisibility(View.VISIBLE);
                time3button.setText(button3);
                time3button.setVisibility(View.VISIBLE);

                time4tv.setText(fourth);
                time4tv.setVisibility(View.VISIBLE);
                time4button.setText(button4);
                time4button.setVisibility(View.VISIBLE);

                time5tv.setText("");
                time5button.setText("");
                time5button.setVisibility(View.INVISIBLE);

                time6tv.setText("");
                time6button.setText("");
                time6button.setVisibility(View.INVISIBLE);
                break;
            case 5:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText(second);
                time2tv.setVisibility(View.VISIBLE);
                time2button.setText(button2);
                time2button.setVisibility(View.VISIBLE);


                time3tv.setText(third);
                time3tv.setVisibility(View.VISIBLE);
                time3button.setText(button3);
                time3button.setVisibility(View.VISIBLE);

                time4tv.setText(fourth);
                time4tv.setVisibility(View.VISIBLE);
                time4button.setText(button4);
                time4button.setVisibility(View.VISIBLE);

                time5tv.setText(fifth);
                time5tv.setVisibility(View.VISIBLE);
                time5button.setText(button5);
                time5button.setVisibility(View.VISIBLE);

                time6tv.setText("");
                time6button.setText("");
                time6button.setVisibility(View.INVISIBLE);
                break;
            case 6:
                time1tv.setText(first);
                time1tv.setVisibility(View.VISIBLE);
                time1button.setVisibility(View.VISIBLE);
                time1button.setText(button1);

                time2tv.setText(second);
                time2tv.setVisibility(View.VISIBLE);
                time2button.setText(button2);
                time2button.setVisibility(View.VISIBLE);


                time3tv.setText(third);
                time3tv.setVisibility(View.VISIBLE);
                time3button.setText(button3);
                time3button.setVisibility(View.VISIBLE);

                time4tv.setText(fourth);
                time4tv.setVisibility(View.VISIBLE);
                time4button.setText(button4);
                time4button.setVisibility(View.VISIBLE);

                time5tv.setText(fifth);
                time5tv.setVisibility(View.VISIBLE);
                time5button.setText(button5);
                time5button.setVisibility(View.VISIBLE);

                time6tv.setText(sixth);
                time6tv.setVisibility(View.VISIBLE);
                time6button.setText(button6);
                time6button.setVisibility(View.VISIBLE);
                break;
        }//switch


    }//onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }//onNothingSelected
}//SettingsFragment class

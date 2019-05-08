package com.abc.danielharrington.betterdays;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    public interface TimePickedListener {
        void onTimePicked(String time);
    }//TimePickedListener interface

    private TimePickedListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }//onCreateDialog method

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String time = hourOfDay + ":" + minute;
        Toast.makeText(getActivity(), "Time Set", Toast.LENGTH_LONG).show();
        listener.onTimePicked(time);
    }//onTimeSet method

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof TimePickedListener){
            listener = (TimePickedListener) context;
        }else{
            throw new RuntimeException(context.toString() + " must implement TimePickedListener");
        }//if/else
    }//onAttach method

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }//onDetach method
}//TimePickerFragment class

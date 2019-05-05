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
import android.widget.Spinner;

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        getActivity().setTitle("Settings");

        Spinner spinner = view.findViewById(R.id.per_day_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.quotes_per_day_numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        return view;
    }//onCreateView method


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selection = parent.getItemAtPosition(position).toString(); //for quotes per day
        int perDay = Integer.parseInt(selection);
    }//onItemSelected

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }//onNothingSelected
}//SettingsFragment class

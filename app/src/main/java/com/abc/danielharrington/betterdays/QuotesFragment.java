package com.abc.danielharrington.betterdays;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class QuotesFragment extends Fragment {


    private DrawerLayout drawer;
    View rootView;

    public TextView testTV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.quotes_fragment, container, false);

        getActivity().setTitle("Quotes");

        return rootView;
    }//onCreateView method



}//QuotesFragment class

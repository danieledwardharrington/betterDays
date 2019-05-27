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
    private String BG_THEME;
    View rootView;

    private RelativeLayout defaultLayout;
    private RelativeLayout forestLayout;
    private RelativeLayout beachLayout;
    private RelativeLayout spaceLayout;
    private RelativeLayout mountainLayout;

    public TextView testTV;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.quotes_fragment, container, false);



        getActivity().setTitle("Quotes");

        defaultLayout = rootView.findViewById(R.id.default_layout);

        return rootView;
    }//onCreateView method

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        testTV = rootView.findViewById(R.id.quote_text_view);

    }

    public void updateTheme(String theme){
      testTV.setText(theme);

    }//updateTheme method

    //method to change the quotes fragment background based on user preference
    private void setQuotesBackground(){


    }//setQuotesBackground method

}//QuotesFragment class

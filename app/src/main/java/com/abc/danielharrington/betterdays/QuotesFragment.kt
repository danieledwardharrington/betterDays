package com.abc.danielharrington.betterdays

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.*

class QuotesFragment() : Fragment() {

    companion object {

        var QUOTES_THEME: String? = null

        //for notifications
        var quoteTextView: TextView? = null
        var speakerTextView: TextView? = null
    }//companion object

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //loadData()

        var rootView = inflater.inflate(R.layout.quotes_fragment, container, false)

        activity!!.title = "Quotes"

        quoteTextView = rootView.findViewById(R.id.quote_text_view)
        speakerTextView = rootView.findViewById(R.id.speaker_text_view)

        //when for the background
        when (QUOTES_THEME){
            "Forest" ->{
                rootView.setBackgroundResource(R.drawable.img_forest)
            }//forest
            "Beach" ->{
                rootView.setBackgroundResource(R.drawable.img_beach)
            }//beach
            "Space" ->{
                rootView.setBackgroundResource(R.drawable.img_space)
            }//space
            "Mountain" ->{
                rootView.setBackgroundResource(R.drawable.img_mountain)
            }//mountain
            "Default" ->{
                rootView.setBackgroundResource(R.color.signBlue)
            }//default
            else ->{
                rootView.setBackgroundResource(R.color.signBlue)
            }//else

        }//when

        return rootView
    }//onCreateView method

    /*
    fun updateTheme(theme: String?) {
                when (QUOTES_THEME){
            "Forest" ->{
                rootView.setBackgroundResource(R.drawable.img_forest)
            }//forest
            "Beach" ->{
                rootView.setBackgroundResource(R.drawable.img_beach)
            }//beach
            "Space" ->{
                rootView.setBackgroundResource(R.drawable.img_space)
            }//space
            "Mountain" ->{
                rootView.setBackgroundResource(R.drawable.img_mountain)
            }//mountain
            "Default" ->{
                rootView.setBackgroundResource(R.color.signBlue)
            }//default
            else ->{
                rootView.setBackgroundResource(R.color.signBlue)
            }//else

        }//when
    }//updateTheme


    //method to load the saved data
    fun loadData() {
        val context = context

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        QUOTES_THEME = sharedPreferences.getString(SettingsFragment.THEME_PREF, "Beach")
    }//loadData method
    */

}//QuotesFragment class

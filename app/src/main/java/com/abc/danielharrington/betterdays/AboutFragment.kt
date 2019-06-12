package com.abc.danielharrington.betterdays

/*
Quick little thank you for the download and a bit about my personal
interest in mental health and motivations around this app
 */

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AboutFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.about_fragment, container, false)

        activity!!.title = "About"

        return view
    }//onCreateView method


}//AboutFragment class

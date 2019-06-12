package com.abc.danielharrington.betterdays

/*
Just an opening "splash" page. Simple. Will not be able to return here from nav
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class WelcomeFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView = inflater.inflate(R.layout.welcome_fragment, container, false)
        return rootView
    }//onCreateView
}
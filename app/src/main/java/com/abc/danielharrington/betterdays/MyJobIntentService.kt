package com.abc.danielharrington.betterdays

/*
I was having some trouble getting notifications with AlarmManager
and BroadcastReceiver to continue happening after the app was
swiped away from the "Recent Apps" section, something users do a
lot. Using JobIntentService was a suggestion from StackOverflow,
should also help with the aggressive force closing of apps that
Chinese ROMs do.
 */

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

class MyJobIntentService: JobIntentService() {

    var TAG: String = ""

    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork")

        AlertReceiver.setAlarms(applicationContext)

    }//onHandleWork method

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
    }//onCreate method

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }//onDestroy method

    override fun onStopCurrentWork(): Boolean {
        Log.d(TAG, "onStopCurrentWork")
        return super.onStopCurrentWork()
    }//onStopCurrentWork method


    companion object {
        val JOB_ID = 1000

        fun enqueueWork(context: Context, work: Intent) {
            enqueueWork(context, AlertReceiver::class.java, JOB_ID, work)
        }//enqueueWork method

    }//companion
}//MyJobIntentService class
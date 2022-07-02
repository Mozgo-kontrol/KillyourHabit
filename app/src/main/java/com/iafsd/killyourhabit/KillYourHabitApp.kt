package com.iafsd.killyourhabit

import android.app.Application
import android.util.Log
import com.iafsd.killyourhabit.repository.DaggerKYHApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KillYourHabitApp: Application() {


    val TAG = "KillYourHabitApp"

   val appComponent = DaggerKYHApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        initialApp()
    }

    private fun initialApp(){




        //appComponent.repository().signOutUserv2()

     // Reference to the application graph that is used across the whole app
     Log.i(TAG, "Initial App")
    }
}
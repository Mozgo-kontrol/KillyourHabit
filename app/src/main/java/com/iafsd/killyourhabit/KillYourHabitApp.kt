package com.iafsd.killyourhabit

import android.app.Application
import android.util.Log
import com.iafsd.killyourhabit.repository.DaggerKYHApplicationComponent
import com.iafsd.killyourhabit.repository.KYHApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KillYourHabitApp: Application() {


    val TAG = "KillYourHabitApp"

    val appComponent: KYHApplicationComponent = DaggerKYHApplicationComponent.create()

    override fun onCreate() {
        super.onCreate()
        initialApp()
    }

    private fun initialApp(){
        val network = appComponent.firebase()
        // Reference to the application graph that is used across the whole app
         Log.i(TAG, "Initial App user ist registered -> ${network.isUserAuth()} ")
    }
}
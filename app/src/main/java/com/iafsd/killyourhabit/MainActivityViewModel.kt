package com.iafsd.killyourhabit

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val userRepository: UserRepositoryImpl) : ViewModel(),
    LifecycleObserver {

    private val TAG: String = MainActivityViewModel::class.java.simpleName
    fun isUserAuth() = userRepository.isUserAuth()

    init {

        Log.d( TAG, "init repository : ${userRepository.isUserAuth()}" )
    }


}
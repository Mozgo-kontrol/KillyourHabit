package com.iafsd.killyourhabit.screens.settings

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.iafsd.killyourhabit.admob.AdMob.addInterstitialCallbacks
import com.iafsd.killyourhabit.admob.AdMob.showInterstitial
import com.iafsd.killyourhabit.repository.UserRepositoryImpl
import com.iafsd.killyourhabit.ui.common.CustomGlobal
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val handle: SavedStateHandle,
    private val userRepositoryImpl: UserRepositoryImpl,
    @ApplicationContext application: Context
): ViewModel() {
    private  var isADMobShown = false

    private val TAG: String = "SettingsViewModel"

    init {}

     fun showADMob(context: Context){
         if (!isADMobShown && CustomGlobal.isAdsOn){
             //loadInterstitial(context)
             addInterstitialCallbacks(context)
             showInterstitial(context)
             isADMobShown = true
         }
    }
}
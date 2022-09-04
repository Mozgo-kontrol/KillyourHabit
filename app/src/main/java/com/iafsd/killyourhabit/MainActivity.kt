package com.iafsd.killyourhabit

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.iafsd.killyourhabit.admob.AdMob.addInterstitialCallbacks
import com.iafsd.killyourhabit.admob.AdMob.loadInterstitial
import com.iafsd.killyourhabit.navigation.SetupNavGraph
import com.iafsd.killyourhabit.ui.common.CustomGlobal
import com.iafsd.killyourhabit.ui.theme.KillYourHabitTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalComposeUiApi
@AndroidEntryPoint
@InternalCoroutinesApi
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isAuth = viewModel.isUserAuth()
        setContent {
            KillYourHabitTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    // Navigation()
                    val navController = rememberNavController()
                    Log.wtf("SSS", "onCreate : isUserAuth  $isAuth ")
                    SetupNavGraph(navController, isAuth)
                }
            }
        }

        //ads is on or off
        if(CustomGlobal.isAdsOn){
            MobileAds.initialize(this) {
                //load the interstitial ad
                loadInterstitial(this)
                // add the interstitial ad callbacks
                addInterstitialCallbacks(this)
            }
        }
    }
}

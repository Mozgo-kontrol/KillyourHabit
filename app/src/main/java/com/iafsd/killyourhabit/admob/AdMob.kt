package com.iafsd.killyourhabit.admob

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.tools.findActivity

var mInterstitialAd: InterstitialAd? = null
//interstitial ad
object AdMob {

    private val TAG = AdMob::class.java.simpleName

    fun loadInterstitial(context: Context){

        InterstitialAd.load(
            context,
            context.getString(R.string.ad_id_interstitial),
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback(){
                override fun onAdLoaded(interstitial: InterstitialAd) {
                    mInterstitialAd = interstitial
                    Log.d(TAG, context.getString(R.string.ad_was_loaded))
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    mInterstitialAd = null
                    Log.d(TAG, context.getString(R.string.on_ad_failed_to_load, loadAdError.message))
                }

            }

        )
    }

    fun addInterstitialCallbacks(context: Context){
        Log.d(TAG,  context.getString(R.string.call_back_added))
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback(){

            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, context.getString(R.string.on_dismissed_full_screen_callback))
                //mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(loadAdError: AdError) {

                Log.wtf(TAG, context.getString(R.string.on_ad_failed_to_show_full_screen,loadAdError.message))
            }

            override fun onAdShowedFullScreenContent() {
                mInterstitialAd = null
                loadInterstitial(context)
                Log.wtf(TAG, context.getString(R.string.on_ad_showed_fullscreen_content))
            }

        }

    }

    fun showInterstitial(context: Context){

        val activity = context.findActivity()
        if(mInterstitialAd!=null){
            mInterstitialAd?.show(activity!!)
        }
        else{
            Log.d(TAG, context.getString(R.string.interstiltial_was_not_ready))

        }

    }


}

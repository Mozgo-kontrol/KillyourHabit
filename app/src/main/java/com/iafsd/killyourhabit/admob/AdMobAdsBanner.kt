package com.iafsd.killyourhabit.admob

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.tools.GridUnit

enum class AdBannerSize{

    STAND_BANNER,
    IN_LINE_ADAPTIVE_BANNER,
    ANCHORED_ADAPTIVE_BANNER,

}

@SuppressLint("ResourceAsColor")
@Composable
fun AdMobAdsBanner (
    adWidth: Int = LocalConfiguration.current.screenWidthDp - 8,
    adBannerSize: AdBannerSize = AdBannerSize.STAND_BANNER,
    context: Context = LocalContext.current,
    adUnitBannerId: String
){

    val size : AdSize = when(adBannerSize){
        AdBannerSize.IN_LINE_ADAPTIVE_BANNER->  {
            AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
                context,
                adWidth
            )}
        AdBannerSize.ANCHORED_ADAPTIVE_BANNER->{
            AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                context,
                adWidth
            )
        }
        else -> {AdSize.BANNER}
    }
    // shows an inline adaptive banner test ad
    val bgColor: Int = R.color.white
    AndroidView(
        modifier = Modifier
            .padding(start = GridUnit.half.dp, end = GridUnit.half.dp).background(MaterialTheme.colors.background),
        factory = {
            AdView(it).apply {
                adSize = size
                adUnitId = adUnitBannerId
                setBackgroundColor(bgColor)
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}
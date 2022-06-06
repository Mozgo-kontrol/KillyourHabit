package com.iafsd.killyourhabit.admob

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.iafsd.killyourhabit.R
import com.iafsd.killyourhabit.admob.AdMob.loadInterstitial
import com.iafsd.killyourhabit.admob.AdMob.showInterstitial

@Composable
fun ComposeAdMobAds() {
    val adWidth = LocalConfiguration.current.screenWidthDp - 32
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Adaptive Banner",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // shows an adaptive banner test ad
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                        context,
                        adWidth
                    )
                    adUnitId = context.getString(R.string.ad_id_banner)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )

        Text(
            text = "Inline Adaptive Banner",
            modifier = Modifier.padding(16.dp)
        )

        // shows an inline adaptive banner test ad
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(
                        context,
                        adWidth
                    )
                    adUnitId = context.getString(R.string.ad_id_banner)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )

        Text(
            text = "Regular Banner",
            modifier = Modifier.padding(16.dp)
        )

        // shows a traditional banner test ad
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    adSize = AdSize.BANNER
                    adUnitId = context.getString(R.string.ad_id_banner)
                    loadAd(AdRequest.Builder().build())
                }
            }
        )

        // shows an interstitial ad on button click (on the first click only)
        Button(
            onClick = {
                loadInterstitial(context)
                showInterstitial(context)
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Show Interstitial")
        }

        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello Ad Network!")
            Text(text = "More texts")
        }

        Text(text = "And some more texts")
    }
}

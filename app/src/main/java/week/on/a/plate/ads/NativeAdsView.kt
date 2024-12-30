package week.on.a.plate.ads

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdEventListener
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.template.NativeBannerView

@Composable
fun NativeAdLoader() {
    val context = LocalContext.current
    //state
    val nativeAds = remember { mutableStateListOf<NativeAd>() }
    
    LaunchedEffect(Unit) {
        loadNativeAd(nativeAds, context)
    }
    //todo place row
    if (nativeAds.isNotEmpty()){
        NativeAdRow(nativeAds.first())
    }
}

@Composable
fun NativeAdRow(nativeAd: NativeAd) {
    val context = LocalContext.current
    val adView = remember { NativeBannerView(context) }
    nativeAd.setNativeAdEventListener(NativeAdEventLogger())
    adView.setAd(nativeAd)
    AndroidView(
        factory = { adView },
        modifier = Modifier.fillMaxWidth(),
        update = { adView.setAd(nativeAd) }
    )
}

private fun loadNativeAd(nativeAds: SnapshotStateList<NativeAd>, context: Context) {
    val nativeAdLoader = NativeAdLoader(context).apply {
        setNativeAdLoadListener(object : NativeAdLoadListener {
            override fun onAdFailedToLoad(error: AdRequestError) {
                Log.e("Ad", error.toString())
            }

            override fun onAdLoaded(nativeAd: NativeAd) {
                nativeAds.add(nativeAd)
            }
        })
    }
    nativeAdLoader.loadAd(NativeAdRequestConfiguration.Builder("R-M-13419544-2").build())
}

private class NativeAdEventLogger : NativeAdEventListener {
    override fun onAdClicked() {
        // Called when a click is recorded for an ad.
    }

    override fun onLeftApplication() {
        // Called when the user is about to leave the application (e.g., to go to the browser), as a result of clicking on the ad.
    }

    override fun onReturnedToApplication() {
        // Called when the user returns to the application after a click.
    }

    override fun onImpression(data: ImpressionData?) {
        // Called when an impression is recorded for an ad.
    }
}
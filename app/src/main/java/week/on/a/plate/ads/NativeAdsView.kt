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
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration
import com.yandex.mobile.ads.nativeads.template.NativeBannerView

@Composable
fun NativeAdRow(id: String) {
    val context = LocalContext.current
    val nativeAds = remember { mutableStateListOf<NativeAd>() }

    LaunchedEffect(Unit) {
        loadNativeAd(id, nativeAds, context)
    }

    if (nativeAds.isNotEmpty()) {
        NativeAdItem(nativeAds.first())
    }
}

@Composable
fun NativeAdItem(nativeAd: NativeAd) {
    val context = LocalContext.current
    val adView = remember { NativeBannerView(context) }
    adView.setAd(nativeAd)
    AndroidView(
        factory = { adView },
        modifier = Modifier.fillMaxWidth(),
        update = { adView.setAd(nativeAd) }
    )
}

private fun loadNativeAd(id: String, nativeAds: SnapshotStateList<NativeAd>, context: Context) {
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
    nativeAdLoader.loadAd(NativeAdRequestConfiguration.Builder(id).build())
}
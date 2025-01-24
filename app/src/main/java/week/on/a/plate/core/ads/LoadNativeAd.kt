package week.on.a.plate.core.ads

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration

fun loadNativeAd(
    id: String,
    nativeAds: SnapshotStateList<NativeAd>,
    context: Context,
    nativeLoader: MutableState<NativeAdLoader?>
) {
    if (nativeLoader.value == null){
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
        nativeLoader.value = nativeAdLoader
    }

    nativeLoader.value?.loadAd(NativeAdRequestConfiguration.Builder(id).build())
}
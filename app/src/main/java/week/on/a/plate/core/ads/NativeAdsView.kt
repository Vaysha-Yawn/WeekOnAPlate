package week.on.a.plate.core.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.yandex.mobile.ads.nativeads.NativeAd
import com.yandex.mobile.ads.nativeads.NativeAdLoader
import com.yandex.mobile.ads.nativeads.template.NativeBannerView

@Composable
fun NativeAdRow(id: String) {
    val context = LocalContext.current
    val nativeAds = remember { mutableStateListOf<NativeAd>() }
    val nativeLoader = remember { mutableStateOf<NativeAdLoader?>(null) }
    LaunchedEffect(Unit) {
        loadNativeAd(id, nativeAds, context, nativeLoader)
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


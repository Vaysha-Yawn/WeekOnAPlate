package week.on.a.plate.core.uitools.webview

import android.os.Build
import android.os.StrictMode
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.webkit.WebSettingsCompat

fun WebView.setSettings(isDark: Boolean) {
    val builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    settings.javaScriptEnabled = true
    isSoundEffectsEnabled = true
    keepScreenOn = true
    settings.builtInZoomControls = true
    settings.displayZoomControls = false
    settings.domStorageEnabled = true
    settings.setSupportZoom(true)
    settings.setSupportMultipleWindows(false)
    settings.javaScriptCanOpenWindowsAutomatically = true
    settings.loadsImagesAutomatically = true
    setLayerType(WebView.LAYER_TYPE_HARDWARE, null)
    settings.setRenderPriority(WebSettings.RenderPriority.HIGH)
    settings.cacheMode = WebSettings.LOAD_DEFAULT
    settings.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    isVerticalScrollBarEnabled = true
    isHorizontalScrollBarEnabled = true
    settings.mediaPlaybackRequiresUserGesture = true
    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptThirdPartyCookies(this, true)
    val userAgent = System.getProperty("http.agent")
    settings.userAgentString = userAgent!! + context.packageName

    if (Build.VERSION.SDK_INT>=29){
        settings.forceDark = if(isDark) WebSettingsCompat.FORCE_DARK_OFF else WebSettingsCompat.FORCE_DARK_ON
    }
    if (Build.VERSION.SDK_INT>=33){
        settings.setAlgorithmicDarkeningAllowed(true)
    }
}
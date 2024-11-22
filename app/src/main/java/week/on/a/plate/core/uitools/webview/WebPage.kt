package week.on.a.plate.core.uitools.webview

import android.os.Build
import android.webkit.WebView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.webkit.WebSettingsCompat
import week.on.a.plate.core.Event

@Composable
fun WebPage(url: MutableState<String>, webview: MutableState<WebView?>, onEvent: (Event) -> Unit, allowGo:Boolean) {
    val isDark = isSystemInDarkTheme()
    AndroidView(
        { context ->
            if (webview.value == null) {
                webview.value = WebView(context)
                val view = webview.value

                view!!.webViewClient = MyWebViewClient(url, allowGo)
                view.webChromeClient = MyWebChromeClient(webview)
                view.setSettings()
                if (Build.VERSION.SDK_INT>=29){
                    view.settings.forceDark = if(isDark) WebSettingsCompat.FORCE_DARK_OFF else WebSettingsCompat.FORCE_DARK_ON
                }
                if (Build.VERSION.SDK_INT>=33){
                    view.settings.setAlgorithmicDarkeningAllowed(true)
                }

                view.setOnLongClickListener {
                    view.detectTouch(onEvent)
                }
                view.loadUrl(url.value)
            } else {
                if (webview.value!!.url != url.value) {
                    webview.value!!.loadUrl(url.value)
                }
            }
            return@AndroidView webview.value!!
        }, modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    )
}


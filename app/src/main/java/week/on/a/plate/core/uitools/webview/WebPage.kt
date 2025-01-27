package week.on.a.plate.core.uitools.webview

import android.webkit.WebView
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import week.on.a.plate.core.Event

@Composable
fun WebPage(
    url: MutableState<String>,
    webview: MutableState<WebView?>,
    onEvent: (Event) -> Unit,
    allowGo: WhenGoFromWebView
) {
    val isDark = isSystemInDarkTheme()
    AndroidView(
        { context ->
            if (webview.value == null) {
                webview.value = WebView(context)
                val view = webview.value
                view!!.webChromeClient = MyWebChromeClient(webview)
                view.setSettings(isDark)

                view.setOnLongClickListener {
                    view.detectTouch(onEvent)
                }
                view.webViewClient = MyWebViewClient(url.value, url, allowGo)
                view.loadUrl(url.value)
            } else {
                if (webview.value!!.url != url.value) {
                    webview.value!!.loadUrl(url.value)
                }
            }
            return@AndroidView webview.value!!
        }, modifier = Modifier
            .fillMaxSize()
            .animateContentSize(), update = {
            webview.value?.loadUrl(url.value)
        }

    )
}


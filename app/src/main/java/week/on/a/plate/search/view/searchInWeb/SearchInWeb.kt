package week.on.a.plate.search.view.searchInWeb

import android.os.StrictMode
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import week.on.a.plate.core.uitools.buttons.DoneButton

//todo url state to vm
// screen to new destination
@Composable
fun SearchInWeb(search: String, onSave:(String)->Unit) {
    val url = remember {
        mutableStateOf("https://duckduckgo.com/?q=$search")
    }
    Scaffold(floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
        DoneButton(text = "Сохранить эту ссылку") {
            onSave(url.value)
        }
    }) { innerPadding ->
        innerPadding
        AndroidView(
            { context ->
                val builder = StrictMode.VmPolicy.Builder()
                StrictMode.setVmPolicy(builder.build())
                val view = WebView(context)
                view.settings.javaScriptEnabled = true
                view.isSoundEffectsEnabled = true
                view.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        url.value = request!!.url.toString()
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                view.webChromeClient = object : WebChromeClient() {}
                view.keepScreenOn = true
                view.settings.setSupportZoom(true)
                view.settings.setSupportMultipleWindows(true)
                view.settings.javaScriptCanOpenWindowsAutomatically = true
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptThirdPartyCookies(view, true)
                val userAgent = System.getProperty("http.agent")
                view.settings.setUserAgentString(userAgent + context.packageName)
                view.loadUrl(url.value)
                return@AndroidView view
            }, modifier = Modifier
                .fillMaxSize()
                .animateContentSize()
        )
    }
}
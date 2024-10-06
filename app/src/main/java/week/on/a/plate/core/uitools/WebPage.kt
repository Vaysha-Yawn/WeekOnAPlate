package week.on.a.plate.core.uitools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Message
import android.os.StrictMode
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.event.MainEvent
import java.security.AccessController.getContext



@Composable
fun WebPage(url:MutableState<String>, webview:MutableState<WebView?>, onEvent:(Event)->Unit, ){
    AndroidView(
        { context ->
            if (webview.value == null) {
                webview.value = WebView(context)
                val view = webview.value
                view!!.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        url.value = request!!.url.toString()
                        return super.shouldOverrideUrlLoading(view, request)
                    }
                }
                view.webChromeClient = object : WebChromeClient() {
                    override fun onCreateWindow(
                        newview: WebView?,
                        isDialog: Boolean,
                        isUserGesture: Boolean,
                        resultMsg: Message?
                    ): Boolean {
                        val transport = resultMsg!!.obj as WebViewTransport
                        transport.webView = view
                        resultMsg.sendToTarget()
                        return true
                    }
                }
                view.setSettings()

                view.setOnLongClickListener {
                    val result = view.getHitTestResult()
                    if (result.type == WebView.HitTestResult.IMAGE_TYPE ||
                        result.type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE
                    ) {

                        val imageUrl = result.extra
                        copyLinkToClipboard(context, imageUrl, onEvent)
                        true
                    } else {
                        false
                    }
                }

                view.loadUrl(url.value)
            }
            return@AndroidView webview.value!!
        }, modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    )
}

private fun copyLinkToClipboard(context: Context, url: String?, onEvent:(Event)->Unit) {
    if (url != null) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Image URL", url)
        clipboard.setPrimaryClip(clip)

        onEvent(MainEvent.ShowSnackBar("Ссылка на изображение скопирована"))
    }
}


fun WebView.setSettings(){
    val builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
    settings.javaScriptEnabled = true
    isSoundEffectsEnabled = true
    keepScreenOn = true
    settings.setSupportZoom(true)
    settings.setSupportMultipleWindows(false)
    settings.javaScriptCanOpenWindowsAutomatically = true
    val cookieManager = CookieManager.getInstance()
    cookieManager.setAcceptThirdPartyCookies(this, true)
    val userAgent = System.getProperty("http.agent")
    settings.userAgentString = userAgent!! + context.packageName
}
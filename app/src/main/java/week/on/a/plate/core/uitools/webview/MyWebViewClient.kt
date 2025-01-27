package week.on.a.plate.core.uitools.webview

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState


enum class WhenGoFromWebView{
    InsideView, NoGo, DefaultBrowser
}

class MyWebViewClient(private val startUrl: String, val url: MutableState<String>, private val allowGo: WhenGoFromWebView) :
    WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        return when (allowGo) {
            WhenGoFromWebView.InsideView -> {
                url.value = request!!.url.toString()
                super.shouldOverrideUrlLoading(view, request)
            }
            WhenGoFromWebView.NoGo -> {
                true
            }
            WhenGoFromWebView.DefaultBrowser -> {
                view!!.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url.value)))
                true
            }
        }
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        handler?.cancel()
    }
}
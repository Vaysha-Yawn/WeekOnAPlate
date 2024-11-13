package week.on.a.plate.core.uitools.webview

import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.MutableState

class MyWebViewClient(val url: MutableState<String>, private val allowGo: Boolean):WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        return if (allowGo){
            url.value = request!!.url.toString()
            return super.shouldOverrideUrlLoading(view, request)
        } else true
    }

    override fun onReceivedSslError(
        view: WebView?,
        handler: SslErrorHandler?,
        error: SslError?
    ) {
        handler?.cancel()
    }
}
package week.on.a.plate.core.uitools.webview

import android.os.Message
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import androidx.compose.runtime.MutableState

class MyWebChromeClient(val webview: MutableState<WebView?>):WebChromeClient() {
    override fun onCreateWindow(
        newview: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        val transport = resultMsg!!.obj as WebViewTransport
        transport.webView = webview.value
        resultMsg.sendToTarget()
        return true
    }

    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        //todo
        // Нужно реализовать отображение view в полноэкранном режиме.
        super.onShowCustomView(view, callback)
    }

    override fun onHideCustomView() {
        // Реализуйте логику для выхода из полноэкранного режима.
        super.onHideCustomView()
    }
}
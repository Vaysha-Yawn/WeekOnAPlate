package week.on.a.plate.screens.documentsWeb.state

import android.webkit.WebView
import androidx.compose.runtime.mutableStateOf

class DocumentsWebUIState() {
    val webView = mutableStateOf<WebView?>(null)
    val url = mutableStateOf<String>("")
}
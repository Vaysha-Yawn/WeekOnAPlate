package week.on.a.plate.screens.additional.ppAndTermsOfUse.state

import android.webkit.WebView
import androidx.compose.runtime.mutableStateOf

class DocumentsWebUIState() {
    val webViewPP = mutableStateOf<WebView?>(null)
    val webViewTerms = mutableStateOf<WebView?>(null)
    val url = mutableStateOf<String>("")
    val isForPP = mutableStateOf<Boolean>(false)
}
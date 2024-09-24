package week.on.a.plate.recipeFullScreen.view.source

import android.os.Message
import android.os.StrictMode
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import week.on.a.plate.core.data.example.recipeTom
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun RecipeDetailsSource(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.value == null) return
    AndroidView(
        { context ->
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
            val view = WebView(context)
            view.settings.javaScriptEnabled = true
            view.isSoundEffectsEnabled = true
            view.webViewClient = object : WebViewClient() {
                /*override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return if (request?.url?.equals(state.recipe.value?.link) == true) {
                        super.shouldOverrideUrlLoading(view, request)
                    } else {
                        true
                    }
                }*/
            }
            view.webChromeClient = object :WebChromeClient(){}
            view.keepScreenOn = true
            view.settings.setSupportZoom(true)
            view.settings.setSupportMultipleWindows(true)
            view.settings.javaScriptCanOpenWindowsAutomatically = true
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptThirdPartyCookies(view, true)
            val userAgent = System.getProperty("http.agent")
            view.settings.setUserAgentString(userAgent + context.packageName)
            view.loadUrl(state.recipe.value!!.link)
            return@AndroidView view
        }, modifier = Modifier
            .fillMaxSize()
            .animateContentSize()
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSource() {
    WeekOnAPlateTheme {
        RecipeDetailsSource(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }) {}
    }
}
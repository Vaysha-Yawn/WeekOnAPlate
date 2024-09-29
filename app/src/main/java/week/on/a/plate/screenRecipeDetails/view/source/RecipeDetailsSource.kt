package week.on.a.plate.screenRecipeDetails.view.source

import android.os.StrictMode
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.WebPage

@Composable
fun RecipeDetailsSource(state: RecipeDetailsState, onEventMain: (Event) -> Unit, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.value == null) return
    val url = remember {
        mutableStateOf(state.recipe.value!!.link)
    }
    WebPage(url,onEventMain )
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSource() {
    WeekOnAPlateTheme {
        RecipeDetailsSource(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }, {}) {}
    }
}
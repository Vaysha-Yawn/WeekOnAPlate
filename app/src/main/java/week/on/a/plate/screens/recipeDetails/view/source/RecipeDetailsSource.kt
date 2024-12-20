package week.on.a.plate.screens.recipeDetails.view.source

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.webview.WebPage
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState

@Composable
fun RecipeDetailsSource(state: RecipeDetailsState, onEventMain: (Event) -> Unit) {
    val url = remember {
        mutableStateOf(state.recipe.link)
    }
    WebPage(url, state.webview, onEventMain, false)
    val mess = if (state.recipe.link.startsWith("http")) {
        stringResource(R.string.page_is_loading)
    } else {
        stringResource(R.string.no_source)
    }
    TextTitle(mess,
        Modifier
            .fillMaxWidth()
            .padding(top = 24.dp), textAlign = TextAlign.Center)
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSource() {
    WeekOnAPlateTheme {
        RecipeDetailsSource(RecipeDetailsState().apply {
            recipe = recipeTom
        }) {}
    }
}
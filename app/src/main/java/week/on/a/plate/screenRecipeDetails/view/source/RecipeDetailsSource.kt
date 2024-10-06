package week.on.a.plate.screenRecipeDetails.view.source

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.WebPage

@Composable
fun RecipeDetailsSource(state: RecipeDetailsState, onEventMain: (Event) -> Unit,) {
    val url = remember {
        mutableStateOf(state.recipe.value.link)
    }
    WebPage(url,state.webview, onEventMain )
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSource() {
    WeekOnAPlateTheme {
        RecipeDetailsSource(RecipeDetailsState().apply {
            recipe = mutableStateOf(recipeTom)
        }) {}
    }
}
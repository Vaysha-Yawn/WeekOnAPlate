package week.on.a.plate.recipeFullScreen.view.start

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun RecipeDetailsTabs(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsTabs() {
    WeekOnAPlateTheme {
        RecipeDetailsTabs(RecipeDetailsState()) {}
    }
}
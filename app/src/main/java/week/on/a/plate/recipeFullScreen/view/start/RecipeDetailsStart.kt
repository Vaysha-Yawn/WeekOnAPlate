package week.on.a.plate.recipeFullScreen.view.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.data.example.recipeTom
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.logic.RecipeDetailsViewModel
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.recipeFullScreen.view.base.RecipeBase
import week.on.a.plate.recipeFullScreen.view.ingredients.RecipeDetailsIngredients
import week.on.a.plate.recipeFullScreen.view.source.RecipeDetailsSource
import week.on.a.plate.recipeFullScreen.view.steps.RecipeDetailsSteps
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun RecipeDetailsStart(vm: RecipeDetailsViewModel) {
    if (vm.state.recipe.value != null) {
        RecipeDetailsStart(vm.state) { event: RecipeDetailsEvent ->
            vm.onEvent(event)
        }
    }
}

@Composable
fun RecipeDetailsStart(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopPanelRecipeDetail(state, onEvent)
        LazyColumn {
            item {
                RecipeBase(state, onEvent)
                RecipeDetailsTabs(state, onEvent)
            }
            item {
                when (state.activeTabIndex.intValue) {
                    0 -> {
                        RecipeDetailsSteps(state, onEvent)
                    }

                    1 -> {
                        RecipeDetailsIngredients(state, onEvent)
                    }

                    2 -> {
                        RecipeDetailsSource(state, onEvent)
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsStart() {
    WeekOnAPlateTheme {
        RecipeDetailsStart(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }) {}
    }
}

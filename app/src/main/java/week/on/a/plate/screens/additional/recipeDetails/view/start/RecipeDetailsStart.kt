package week.on.a.plate.screens.additional.recipeDetails.view.start

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.logic.RecipeDetailsViewModel
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.additional.recipeDetails.view.base.RecipeBase
import week.on.a.plate.screens.additional.recipeDetails.view.ingredients.RecipeDetailsIngredients
import week.on.a.plate.screens.additional.recipeDetails.view.source.RecipeDetailsSource
import week.on.a.plate.screens.additional.recipeDetails.view.steps.RecipeDetailsSteps

@Composable
fun RecipeDetailsStart(
    vm: RecipeDetailsViewModel,
    mainViewModel: MainViewModel
) {
    RecipeDetailsStart(vm.state, { event: Event ->
        mainViewModel.onEvent(event)
    }) { event: RecipeDetailsEvent ->
        vm.onEvent(event)
    }
    MainEventResolve(vm.mainEvent, remember { mutableStateOf(null) }, mainViewModel)
}

@Composable
private fun RecipeDetailsStart(
    state: RecipeDetailsState,
    onEventMain: (Event) -> Unit,
    onEvent: (RecipeDetailsEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        TopPanelRecipeDetail(state.isFavorite.value, onEvent)
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
                        RecipeDetailsSource(state, onEventMain)
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
            recipe = recipeTom
        }, {}) {}
    }
}

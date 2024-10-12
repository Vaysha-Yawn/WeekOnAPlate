package week.on.a.plate.screens.filters.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.logic.FilterViewModel

@Composable
fun FilterStart(
    viewModel: FilterViewModel
) {
    val onEvent = {event: FilterEvent ->
        viewModel.onEvent(event)
    }
    viewModel.state.allIngredientsCategories = viewModel.allIngredients.collectAsState()
    viewModel.state.allTagsCategories = viewModel.allTags.collectAsState()

    Column {
        TopSearchPanelFilter(stateUI = viewModel.state, onEvent)
        FilterScreen(viewModel.state, onEvent)
    }
}
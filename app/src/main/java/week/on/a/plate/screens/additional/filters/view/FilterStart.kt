package week.on.a.plate.screens.additional.filters.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.logic.FilterViewModel

@Composable
fun FilterStart(
    viewModel: FilterViewModel,
    viewModel1: MainViewModel
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
    MainEventResolve(viewModel.mainEvent, viewModel.dialogOpenParams, viewModel1)
}
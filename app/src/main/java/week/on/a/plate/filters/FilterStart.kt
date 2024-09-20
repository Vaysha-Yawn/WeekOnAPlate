package week.on.a.plate.filters

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.filters.navigation.FilterRoute
import week.on.a.plate.search.view.filtersScreen.FilterScreen
import week.on.a.plate.search.view.filtersScreen.TopSearchPanelFilter

@Composable
fun FilterStart(
    arguments: FilterRoute,
    mainVM: MainViewModel,
    viewModel: FilterViewModel = hiltViewModel(),
) {
    viewModel.mainViewModel = mainVM

    val onEvent = { eventData: Event ->
        viewModel.onEvent(eventData)
    }

    Column {
        TopSearchPanelFilter(stateUI = viewModel.stateUI, onEvent)
        FilterScreen(viewModel.stateUI, onEvent)
    }
}
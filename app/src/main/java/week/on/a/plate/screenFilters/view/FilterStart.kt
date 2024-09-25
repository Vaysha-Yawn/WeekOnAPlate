package week.on.a.plate.screenFilters.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.logic.FilterViewModel

@Composable
fun FilterStart(
    viewModel: FilterViewModel
) {
    val onEvent = {event: FilterEvent ->
        viewModel.onEvent(event)
    }
    Column {
        TopSearchPanelFilter(stateUI = viewModel.state, onEvent)
        FilterScreen(viewModel.state, onEvent)
    }
}
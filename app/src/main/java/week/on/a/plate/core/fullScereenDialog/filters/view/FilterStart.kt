package week.on.a.plate.core.fullScereenDialog.filters.view

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import week.on.a.plate.core.fullScereenDialog.filters.event.FilterEvent
import week.on.a.plate.core.fullScereenDialog.filters.logic.FilterViewModel

@Composable
fun FilterStart(
    viewModel: FilterViewModel
) {
    val onEvent = {event:FilterEvent->
        viewModel.onEvent(event)
    }
    Column {
        TopSearchPanelFilter(stateUI = viewModel.state, onEvent)
        FilterScreen(viewModel.state, onEvent)
    }
}
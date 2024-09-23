package week.on.a.plate.core.fullScereenDialog.specifySelection.view

import androidx.compose.runtime.Composable
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.core.fullScereenDialog.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.core.fullScereenDialog.specifySelection.logic.SpecifySelectionViewModel

@Composable
fun SpecifySelectionMain(
    mainViewModel: MainViewModel,
    viewModel: SpecifySelectionViewModel,
) {
    viewModel.mainViewModel = mainViewModel

    val onEvent = { eventData: SpecifySelectionEvent ->
        viewModel.onEvent(eventData)
    }
    val onEventMain = { eventData: MainEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    SpecifyDateScreen(state, onEvent, onEventMain)
}
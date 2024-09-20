package week.on.a.plate.SpecifySelection.view

import androidx.compose.runtime.Composable
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.SpecifySelection.data.SpecifySelectionEvent
import week.on.a.plate.SpecifySelection.logic.SpecifySelectionViewModel

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
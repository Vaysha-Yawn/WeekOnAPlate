package week.on.a.plate.screens.specifySelection.view

import androidx.compose.runtime.Composable
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.logic.SpecifySelectionViewModel

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
    if (state.isDateChooseActive.value){
        CalendarWithMenu(state, onEvent)
    }else{
        viewModel.updateSelections()
        SpecifyDateScreen(state, onEvent, onEventMain)
    }
}
package week.on.a.plate.dialogs.forMenuScreen.editSelection.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.forMenuScreen.editSelection.event.EditSelectionEvent
import week.on.a.plate.dialogs.forMenuScreen.editSelection.state.EditSelectionUIState
import week.on.a.plate.dialogs.timePick.logic.TimePickViewModel
import java.time.LocalTime

class EditSelectionViewModel(
    val mainViewModel: MainViewModel,
    oldData: EditSelectionUIState,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (EditSelectionUIState) -> Unit,
) : DialogViewModel<EditSelectionUIState>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    val state: EditSelectionUIState = oldData

    fun onEvent(event: EditSelectionEvent) {
        when (event) {
            EditSelectionEvent.Close -> closeDialog()
            EditSelectionEvent.Done -> done(state)
            is EditSelectionEvent.ChooseTime -> chooseTime()
        }
    }

    //hide? show?
    private fun chooseTime() {
        viewModelScope.launch {
            TimePickViewModel.launch(mainViewModel, R.string.set_meal_time) {
                state.selectedTime.value = LocalTime.ofSecondOfDay((it))
            }
        }
    }

    companion object {
        fun launch(
            oldData: EditSelectionUIState,
            mainViewModel: MainViewModel, useResult: (EditSelectionUIState) -> Unit
        ) {
            EditSelectionViewModel(
                mainViewModel,
                oldData,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
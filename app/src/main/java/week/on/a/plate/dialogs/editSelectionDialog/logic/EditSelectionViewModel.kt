package week.on.a.plate.dialogs.editSelectionDialog.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.editSelectionDialog.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelectionDialog.state.EditSelectionUIState
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
    val dialogOpenParams: MutableState<DialogOpenParams?> = mutableStateOf(null)
    val mainEvent: MutableState<MainEvent?> = mutableStateOf(null)

    fun onEvent(event: EditSelectionEvent) {
        when (event) {
            EditSelectionEvent.Close -> closeDialog()
            EditSelectionEvent.Done -> done(state)
            is EditSelectionEvent.ChooseTime -> chooseTime()
        }
    }

    private fun chooseTime() {
        val params = TimePickViewModel.TimePickDialogParams(R.string.set_meal_time) {
                state.selectedTime.value = LocalTime.ofSecondOfDay((it))
            }
        dialogOpenParams.value = params
    }

    class EditSelectionDialogParams(
        private val oldData: EditSelectionUIState,
        private val useResult: (EditSelectionUIState) -> Unit
    ) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
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
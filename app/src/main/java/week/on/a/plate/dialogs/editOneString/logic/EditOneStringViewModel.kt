package week.on.a.plate.dialogs.editOneString.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.editOneString.event.EditOneStringEvent
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState


class EditOneStringViewModel(
    oldData: EditOneStringUIState,
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    useResult: suspend (String) -> Unit,
) : DialogViewModel<String>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    val state: EditOneStringUIState = oldData

    fun onEvent(event: EditOneStringEvent) {
        when (event) {
            EditOneStringEvent.Close -> close()
            EditOneStringEvent.Done -> done(state.text.value)
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel, oldData: EditOneStringUIState,
            useResult: suspend (String) -> Unit,
        ) {
            EditOneStringViewModel(
                oldData,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
package week.on.a.plate.dialogs.exitApply.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ExitApplyViewModel(
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    useResult: (ExitApplyEvent) -> Unit,
) : DialogViewModel<ExitApplyEvent>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    fun onEvent(event: ExitApplyEvent) {
        when (event) {
            ExitApplyEvent.Close -> close()
            ExitApplyEvent.Exit -> done(event)
        }
    }

    companion object {
        fun launch(mainViewModel: MainViewModel, useResult: (ExitApplyEvent) -> Unit) {
            ExitApplyViewModel(
                viewModelScope = mainViewModel.getCoroutineScope(),
                openDialog = mainViewModel::openDialog,
                closeDialog = mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
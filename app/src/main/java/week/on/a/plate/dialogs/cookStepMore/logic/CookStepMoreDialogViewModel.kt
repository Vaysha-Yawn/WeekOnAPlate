package week.on.a.plate.dialogs.cookStepMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent


class CookStepMoreDialogViewModel(
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    useResult: (CookStepMoreEvent) -> Unit
) : DialogViewModel<CookStepMoreEvent>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    fun onEvent(event: CookStepMoreEvent) {
        when (event) {
            CookStepMoreEvent.Close -> close()
            else -> done(event)
        }
    }

    class CookStepMoreDialogDialogParams(private val useResult: (CookStepMoreEvent) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            CookStepMoreDialogViewModel(mainViewModel.getCoroutineScope(), mainViewModel::openDialog, mainViewModel::closeDialog, useResult)
        }
    }
}
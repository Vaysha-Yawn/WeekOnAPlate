package week.on.a.plate.dialogs.cookStepMore.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


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

    companion object{
        fun launch(mainViewModel: MainViewModel, useResult: (CookStepMoreEvent) -> Unit){
            CookStepMoreDialogViewModel(mainViewModel.getCoroutineScope(), mainViewModel::openDialog, mainViewModel::closeDialog, useResult)
        }
    }
}
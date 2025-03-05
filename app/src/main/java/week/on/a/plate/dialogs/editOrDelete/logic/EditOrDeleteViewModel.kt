package week.on.a.plate.dialogs.editOrDelete.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent


class EditOrDeleteViewModel (
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (EditOrDeleteEvent) -> Unit
): DialogViewModel<EditOrDeleteEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    fun onEvent(event: EditOrDeleteEvent){
        when(event) {
            EditOrDeleteEvent.Close -> close()
            else -> done(event)
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel, useResult: (EditOrDeleteEvent) -> Unit
        ) {
            EditOrDeleteViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
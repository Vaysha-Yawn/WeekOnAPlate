package week.on.a.plate.dialogs.editOrDelete.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
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

    class EditOrDeleteDialogParams(private val useResult: (EditOrDeleteEvent) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            EditOrDeleteViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
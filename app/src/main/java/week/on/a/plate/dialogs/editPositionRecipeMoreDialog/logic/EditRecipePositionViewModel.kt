package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.event.ActionMoreRecipePositionEvent

class EditRecipePositionViewModel(
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (ActionMoreRecipePositionEvent) -> Unit
) : DialogViewModel<ActionMoreRecipePositionEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    fun onEvent(event: ActionMoreRecipePositionEvent) {
        when (event) {
            ActionMoreRecipePositionEvent.Close -> close()
            else -> done(event)
        }
    }

    class EditRecipePositionDialogParams(private val useResult: (ActionMoreRecipePositionEvent) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            EditRecipePositionViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
package week.on.a.plate.dialogs.editPositionRecipeMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editPositionRecipeMore.event.ActionMoreRecipePositionEvent
import week.on.a.plate.mainActivity.logic.MainViewModel

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

    companion object {
        fun launch(
            mainViewModel: MainViewModel, useResult: (ActionMoreRecipePositionEvent) -> Unit
        ) {
            EditRecipePositionViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
package week.on.a.plate.dialogs.editPositionRecipeMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editPositionRecipeMore.event.EditRecipePositionEvent
import week.on.a.plate.mainActivity.logic.MainViewModel

class EditRecipePositionViewModel(
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (EditRecipePositionEvent) -> Unit
) : DialogViewModel<EditRecipePositionEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    fun onEvent(event: EditRecipePositionEvent) {
        when (event) {
            EditRecipePositionEvent.Close -> close()
            else -> done(event)
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel, useResult: (EditRecipePositionEvent) -> Unit
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
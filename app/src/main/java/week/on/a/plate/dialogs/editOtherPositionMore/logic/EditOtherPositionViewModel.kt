package week.on.a.plate.dialogs.editOtherPositionMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOtherPositionMore.event.EditOtherPositionEvent
import week.on.a.plate.dialogs.editOtherPositionMore.state.EditOtherPositionUIState
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditOtherPositionViewModel(
    val isForIngredient: Boolean,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (EditOtherPositionEvent) -> Unit,
) : DialogViewModel<EditOtherPositionEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    val state = EditOtherPositionUIState()

    fun onEvent(event: EditOtherPositionEvent) {
        when (event) {
            EditOtherPositionEvent.Close -> close()
            else -> done(event)
        }
    }

    companion object {
        fun launch(
            isForIngredient: Boolean,
            mainViewModel: MainViewModel, useResult: (EditOtherPositionEvent) -> Unit
        ) {
            EditOtherPositionViewModel(
                isForIngredient,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
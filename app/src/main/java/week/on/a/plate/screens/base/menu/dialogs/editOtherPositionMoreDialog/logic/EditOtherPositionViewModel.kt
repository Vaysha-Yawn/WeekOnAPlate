package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.event.OtherPositionMoreEvent
import week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.state.EditOtherPositionUIState


class EditOtherPositionViewModel(
    val isForIngredient: Boolean,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (OtherPositionMoreEvent) -> Unit,
) : DialogViewModel<OtherPositionMoreEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    val state = EditOtherPositionUIState()

    fun onEvent(event: OtherPositionMoreEvent) {
        when (event) {
            OtherPositionMoreEvent.Close -> close()
            else -> done(event)
        }
    }

    companion object {
        fun launch(
            isForIngredient: Boolean,
            mainViewModel: MainViewModel, useResult: (OtherPositionMoreEvent) -> Unit
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
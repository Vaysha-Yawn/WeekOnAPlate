package week.on.a.plate.dialogs.addPositionChoose.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent


class AddPositionViewModel(
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: suspend (AddPositionEvent) -> Unit
) : DialogViewModel<AddPositionEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    fun onEvent(event: AddPositionEvent) {
        when (event) {
            AddPositionEvent.Close -> close()
            else -> done(event)
        }
    }

    class AddPositionDialogParams(private val useResult: suspend (AddPositionEvent) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            AddPositionViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
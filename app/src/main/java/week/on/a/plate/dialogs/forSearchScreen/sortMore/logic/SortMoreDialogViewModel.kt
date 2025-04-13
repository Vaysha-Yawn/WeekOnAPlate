package week.on.a.plate.dialogs.forSearchScreen.sortMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.forSearchScreen.sortMore.event.SortMoreEvent


class SortMoreDialogViewModel(
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (SortMoreEvent) -> Unit
) : DialogViewModel<SortMoreEvent>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    fun onEvent(event: SortMoreEvent) {
        if (event == SortMoreEvent.Close) close()
        else done(event)
    }

    class SortMoreDialogParams(private val useResult: (SortMoreEvent) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            SortMoreDialogViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
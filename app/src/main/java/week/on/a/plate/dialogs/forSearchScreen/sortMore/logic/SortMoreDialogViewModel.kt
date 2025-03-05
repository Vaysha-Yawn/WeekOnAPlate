package week.on.a.plate.dialogs.forSearchScreen.sortMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.forSearchScreen.sortMore.event.SortMoreEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel


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

    companion object {
        fun startDialog(
            mainViewModel: MainViewModel, useResult: (SortMoreEvent) -> Unit
        ) {
            SortMoreDialogViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
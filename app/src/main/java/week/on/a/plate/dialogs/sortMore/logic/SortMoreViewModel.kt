package week.on.a.plate.dialogs.sortMore.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.sortMore.event.SortMoreEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class SortMoreViewModel(
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
        fun launch(
            mainViewModel: MainViewModel, useResult: (SortMoreEvent) -> Unit
        ) {
            SortMoreViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
package week.on.a.plate.dialogs.changePortions.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.changePortions.event.ChangePortionsCountEvent
import week.on.a.plate.dialogs.changePortions.state.ChangePortionsCountUIState

class ChangePortionsCountViewModel(
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    startValued: Int,
    useResult: (Int) -> Unit,
) : DialogViewModel<Int>(
    viewModelScope,
    openDialog,
    closeDialog,
    useResult
) {
    val state = ChangePortionsCountUIState().apply {
        portionsCount.intValue = startValued
    }

    fun onEvent(event: ChangePortionsCountEvent) {
        when (event) {
            ChangePortionsCountEvent.Close -> close()
            ChangePortionsCountEvent.Done -> done(state.portionsCount.intValue)
        }
    }

    class ChangePortionsCountDialogParams(
        private val startValued: Int,
        private val useResult: (Int) -> Unit
    ) : DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            ChangePortionsCountViewModel(
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                startValued,
                useResult
            )
        }
    }
}
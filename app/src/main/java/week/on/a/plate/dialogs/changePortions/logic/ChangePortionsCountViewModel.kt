package week.on.a.plate.dialogs.changePortions.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.dialogs.changePortions.event.ChangePortionsCountEvent
import week.on.a.plate.dialogs.changePortions.state.ChangePortionsCountUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel


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

    companion object {
        fun launch(mainViewModel: MainViewModel, startValued: Int, useResult: (Int) -> Unit) {
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
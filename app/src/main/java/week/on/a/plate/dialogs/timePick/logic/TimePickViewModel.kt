package week.on.a.plate.dialogs.timePick.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.timePick.event.TimePickEvent
import week.on.a.plate.dialogs.timePick.state.TimePickUIState


class TimePickViewModel(
    viewModelScope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    title: Int,
    use: (Long) -> Unit
) : DialogViewModel<Long>(
    viewModelScope,
    openDialog,
    closeDialog,
    use
) {
    val state: TimePickUIState = TimePickUIState(title)

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: TimePickEvent) {
        when (event) {
            TimePickEvent.Close -> close()
            TimePickEvent.Done -> {
                val sec = state.timeState.hour.toLong() * 60 * 60 + state.timeState.minute.toLong() * 60
                done(sec)
            }
        }
    }

    class TimePickDialogParams(private val title: Int, private val useResult: (Long) -> Unit) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            TimePickViewModel(mainViewModel.getCoroutineScope(), mainViewModel::openDialog, mainViewModel::closeDialog, title, useResult)
        }
    }
}
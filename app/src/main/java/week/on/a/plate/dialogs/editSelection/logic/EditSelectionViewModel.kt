package week.on.a.plate.dialogs.editSelection.logic

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editSelection.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import java.time.LocalTime


class EditSelectionViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    var state: EditSelectionUIState = EditSelectionUIState()
    private lateinit var resultFlow: MutableStateFlow<EditSelectionUIState?>

    fun start(): Flow<EditSelectionUIState?> {
        val flow = MutableStateFlow<EditSelectionUIState?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = state
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditSelectionEvent) {
        when (event) {
            EditSelectionEvent.Close -> close()
            EditSelectionEvent.Done -> done()
            is EditSelectionEvent.ChooseTime -> chooseTime(event.context)
        }
    }

    private fun chooseTime(context:Context) {
        mainViewModel.viewModelScope.launch {
            val vm = TimePickViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(context.getString(R.string.set_meal_time)){
                state.selectedTime.value = LocalTime.ofSecondOfDay((it).toLong())
            }
        }
    }

    suspend fun launchAndGet(
        oldData: EditSelectionUIState,
        use: suspend (EditSelectionUIState) -> Unit,
    ) {
        state = oldData
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(state)
            }
        }
    }

}
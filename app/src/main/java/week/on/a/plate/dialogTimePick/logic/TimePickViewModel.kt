package week.on.a.plate.dialogTimePick.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogTimePick.event.TimePickEvent
import week.on.a.plate.dialogTimePick.state.TimePickUIState


class TimePickViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: TimePickUIState = TimePickUIState()
    private lateinit var resultFlow: MutableStateFlow<Int?>

    fun start(): Flow<Int?> {
        val flow = MutableStateFlow<Int?>(null)
        resultFlow = flow
        return flow
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun done() {
        close()
        val sec = state.timeState.hour*60*60 + state.timeState.minute*60
        resultFlow.value = sec
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: TimePickEvent) {
        when (event) {
            TimePickEvent.Close -> close()
            TimePickEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(use: (Int) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
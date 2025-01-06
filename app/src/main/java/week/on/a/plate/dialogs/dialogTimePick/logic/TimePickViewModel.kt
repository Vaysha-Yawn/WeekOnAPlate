package week.on.a.plate.dialogs.dialogTimePick.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.dialogTimePick.event.TimePickEvent
import week.on.a.plate.dialogs.dialogTimePick.state.TimePickUIState


class TimePickViewModel(
    title: Int,
    scope: CoroutineScope,
    val closeDialog: () -> Unit,
    use: (Long) -> Unit
) : DialogViewModel() {

    val state: TimePickUIState = TimePickUIState(title)
    private lateinit var resultFlow: MutableStateFlow<Long?>

    init {
        scope.launch {
            val flow = start()
            flow.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        }
    }

    fun start(): Flow<Long?> {
        val flow = MutableStateFlow<Long?>(null)
        resultFlow = flow
        return flow
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun done() {
        close()
        val sec = state.timeState.hour.toLong() * 60 * 60 + state.timeState.minute.toLong() * 60
        resultFlow.value = sec
    }

    fun close() {
        state.show.value = false
        closeDialog()
    }

    fun onEvent(event: TimePickEvent) {
        when (event) {
            TimePickEvent.Close -> close()
            TimePickEvent.Done -> done()
        }
    }

}
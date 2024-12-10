package week.on.a.plate.dialogs.sortMore.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.sortMore.event.SortMoreEvent
import week.on.a.plate.dialogs.sortMore.state.SortMoreUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class SortMoreViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: SortMoreUIState = SortMoreUIState()
    private lateinit var resultFlow: MutableStateFlow<SortMoreEvent?>

    fun start(): Flow<SortMoreEvent?> {
        val flow = MutableStateFlow<SortMoreEvent?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: SortMoreEvent) {
        close()
        resultFlow.value = event
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: SortMoreEvent) {
        if (event == SortMoreEvent.Close) close()
        else done(event)
    }

    suspend fun launchAndGet( use: (SortMoreEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
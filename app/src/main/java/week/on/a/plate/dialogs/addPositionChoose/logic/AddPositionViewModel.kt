package week.on.a.plate.dialogs.addPositionChoose.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.state.AddPositionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class AddPositionViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = AddPositionUIState()
    private lateinit var resultFlow: MutableStateFlow<AddPositionEvent?>

    fun start(): Flow<AddPositionEvent?> {
        val flow = MutableStateFlow<AddPositionEvent?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: AddPositionEvent) {
        close()
        resultFlow.value = event
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: AddPositionEvent) {
        when (event) {
            AddPositionEvent.Close -> close()
            else -> done(event)
        }
    }

    suspend fun launchAndGet(use: (AddPositionEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
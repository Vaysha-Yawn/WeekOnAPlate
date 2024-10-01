package week.on.a.plate.dialogChangePositionCount.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogChangePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.dialogChangePositionCount.state.ChangePortionsCountUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ChangePortionsCountViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = ChangePortionsCountUIState()
    private lateinit var resultFlow: MutableStateFlow<Int?>

    fun start(): Flow<Int?> {
        val flow = MutableStateFlow<Int?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = state.portionsCount.intValue
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: ChangePortionsCountEvent) {
        when (event) {
            ChangePortionsCountEvent.Close -> close()
            ChangePortionsCountEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(startValued: Int, use: (Int) -> Unit) {
        state.portionsCount.intValue = startValued

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
package week.on.a.plate.core.dialogs.menu.changePositionCount.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.changePositionCount.event.ChangePortionsCountEvent
import week.on.a.plate.core.dialogs.menu.changePositionCount.state.ChangePortionsCountUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class ChangePortionsCountViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: ChangePortionsCountUIState
    private lateinit var resultFlow: MutableStateFlow<Int?>
    var startValue = 0

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
        startValue = startValued
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
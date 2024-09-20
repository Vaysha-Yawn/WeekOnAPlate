package week.on.a.plate.core.dialogs.dialogAbstract.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.dialogAbstract.event.DialogEvent
import week.on.a.plate.core.dialogs.dialogAbstract.state.UIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class DialogViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: UIState
    private lateinit var resultFlow: MutableStateFlow<DialogEvent?>

    fun start(): Flow<DialogEvent?> {
        val flow = MutableStateFlow<DialogEvent?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: DialogEvent) {
        close()
        resultFlow.value = event
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.Close -> close()
            DialogEvent.Done -> done(event)
        }
    }

    suspend fun launchAndGet(use: (DialogEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
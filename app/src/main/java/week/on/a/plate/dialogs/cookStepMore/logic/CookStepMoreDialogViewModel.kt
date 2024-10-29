package week.on.a.plate.dialogs.cookStepMore.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.cookStepMore.event.CookStepMoreEvent
import week.on.a.plate.dialogs.cookStepMore.state.CookStepMoreUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class CookStepMoreDialogViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: CookStepMoreUIState = CookStepMoreUIState()
    private lateinit var resultFlow: MutableStateFlow<CookStepMoreEvent?>

    fun start(): Flow<CookStepMoreEvent?> {
        val flow = MutableStateFlow<CookStepMoreEvent?>(null)
        resultFlow = flow
        return flow
    }

    fun done(event: CookStepMoreEvent) {
        close()
        resultFlow.value = event
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: CookStepMoreEvent) {
        when (event) {
            CookStepMoreEvent.Close -> close()
            else -> done(event)
        }
    }

    suspend fun launchAndGet(use: (CookStepMoreEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
package week.on.a.plate.dialogs.exitApply.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.exitApply.event.ExitApplyEvent
import week.on.a.plate.dialogs.exitApply.state.ExitApplyUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ExitApplyViewModel(): DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = ExitApplyUIState()
    private lateinit var resultFlow: MutableStateFlow< ExitApplyEvent?>

    fun start(): Flow<ExitApplyEvent?> {
        val flow = MutableStateFlow<ExitApplyEvent?>(null)
        resultFlow = flow
        return flow
    }

     fun done(event: ExitApplyEvent) {
         close()
         resultFlow.value = event
    }

     fun close(){
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: ExitApplyEvent){
        when(event) {
            ExitApplyEvent.Close -> close()
            ExitApplyEvent.Exit -> done(event)
        }
    }

    suspend fun launchAndGet( use: (ExitApplyEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
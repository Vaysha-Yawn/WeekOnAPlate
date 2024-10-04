package week.on.a.plate.dialogEditOrDelete.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogEditOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogEditOrDelete.state.EditOrDeleteUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditOrDeleteViewModel (): DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = EditOrDeleteUIState()
    private lateinit var resultFlow: MutableStateFlow< EditOrDeleteEvent?>

    fun start(): Flow<EditOrDeleteEvent?> {
        val flow = MutableStateFlow<EditOrDeleteEvent?>(null)
        resultFlow = flow
        return flow
    }

     fun done(event: EditOrDeleteEvent) {
         close()
         resultFlow.value = event
    }

     fun close(){
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditOrDeleteEvent){
        when(event) {
            EditOrDeleteEvent.Close -> close()
            else -> done(event)
        }
    }

    suspend fun launchAndGet( use: suspend (EditOrDeleteEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
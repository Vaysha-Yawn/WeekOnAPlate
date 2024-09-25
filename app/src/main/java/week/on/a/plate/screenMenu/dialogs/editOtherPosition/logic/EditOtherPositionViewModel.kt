package week.on.a.plate.screenMenu.dialogs.editOtherPosition.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.screenMenu.dialogs.editOtherPosition.state.EditOtherPositionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditOtherPositionViewModel (): DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = EditOtherPositionUIState()
    private lateinit var resultFlow: MutableStateFlow< EditOtherPositionEvent?>

    fun start(): Flow<EditOtherPositionEvent?> {
        val flow = MutableStateFlow<EditOtherPositionEvent?>(null)
        resultFlow = flow
        return flow
    }

     fun done(event: EditOtherPositionEvent) {
         close()
         resultFlow.value = event
    }

     fun close(){
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditOtherPositionEvent){
        when(event){
            EditOtherPositionEvent.Close -> close()
            else -> done(event)
        }
    }

    suspend fun launchAndGet(use: (EditOtherPositionEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
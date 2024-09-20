package week.on.a.plate.core.dialogs.menu.editOtherPosition.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.editOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.core.dialogs.menu.editOtherPosition.state.EditOtherPositionUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


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
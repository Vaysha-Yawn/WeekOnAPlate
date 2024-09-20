package week.on.a.plate.core.dialogs.editRecipePosition.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.editRecipePosition.state.EditRecipePositionUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel

class EditRecipePositionViewModel(): DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: EditRecipePositionUIState = EditRecipePositionUIState()
    private lateinit var resultFlow: MutableStateFlow<EditRecipePositionEvent?>

    fun start(): Flow<EditRecipePositionEvent?> {
        val flow = MutableStateFlow<EditRecipePositionEvent?>(null)
        resultFlow = flow
        return flow
    }

     fun close(){
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditRecipePositionEvent){
        when(event){
            EditRecipePositionEvent.Close -> close()
            else -> {
                resultFlow.value = event
                close()
            }
        }
    }

    suspend fun launchAndGet(use: (EditRecipePositionEvent) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
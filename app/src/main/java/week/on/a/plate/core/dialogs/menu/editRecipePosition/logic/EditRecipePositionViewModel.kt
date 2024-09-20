package week.on.a.plate.core.dialogs.menu.editRecipePosition.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.editRecipePosition.event.EditRecipePositionEvent
import week.on.a.plate.core.dialogs.menu.editRecipePosition.state.EditRecipePositionUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel

class EditRecipePositionViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state: EditRecipePositionUIState = EditRecipePositionUIState()
    private lateinit var resultFlow: MutableStateFlow<EditRecipePositionEvent?>

    fun start(): Flow<EditRecipePositionEvent?> {
        val flow = MutableStateFlow<EditRecipePositionEvent?>(null)
        resultFlow = flow
        return flow
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.CloseDialog)
        state.show.value = false
    }

    fun onEvent(event: EditRecipePositionEvent) {
        when (event) {
            EditRecipePositionEvent.Close -> close()
            else -> {
                close()
                resultFlow.value = event
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
package week.on.a.plate.dialogs.editPositionRecipeMore.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editPositionRecipeMore.event.EditRecipePositionEvent
import week.on.a.plate.dialogs.editPositionRecipeMore.state.EditRecipePositionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel

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
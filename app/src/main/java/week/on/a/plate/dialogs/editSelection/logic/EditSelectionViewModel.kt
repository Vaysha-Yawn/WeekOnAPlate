package week.on.a.plate.dialogs.editSelection.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editSelection.event.EditSelectionEvent
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.dialogs.dialogTimePick.logic.TimePickViewModel
import java.time.LocalTime

class EditSelectionViewModel(
    oldData: EditSelectionUIState,
    private val scope : CoroutineScope,
    val openDialog:(DialogViewModel)->Unit,
    val closeDialog:()->Unit,
    use: suspend (EditSelectionUIState) -> Unit,
) : DialogViewModel() {

    val state: EditSelectionUIState = oldData
    private lateinit var resultFlow: MutableStateFlow<EditSelectionUIState?>

    init {
        openDialog(this)
        scope.launch {
            val flow = start()
            flow.collect { value ->
                if (value != null) {
                    use(state)
                }
            }
        }
    }

    fun start(): Flow<EditSelectionUIState?> {
        val flow = MutableStateFlow<EditSelectionUIState?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        closeDialog()
        resultFlow.value = state
    }

    fun onEvent(event: EditSelectionEvent) {
        when (event) {
            EditSelectionEvent.Close -> closeDialog()
            EditSelectionEvent.Done -> done()
            is EditSelectionEvent.ChooseTime -> chooseTime()
        }
    }

    private fun chooseTime() {
        scope.launch {
            val vm = TimePickViewModel (R.string.set_meal_time, scope, closeDialog){
                state.selectedTime.value = LocalTime.ofSecondOfDay((it))
            }
            openDialog(vm)
        }
    }

}
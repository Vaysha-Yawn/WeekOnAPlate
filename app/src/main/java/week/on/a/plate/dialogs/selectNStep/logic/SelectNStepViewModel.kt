package week.on.a.plate.dialogs.selectNStep.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.core.appDialogExampleStructure.dialogAbstract.event.DialogEvent
import week.on.a.plate.dialogs.selectNStep.event.SelectNStepEvent
import week.on.a.plate.dialogs.selectNStep.state.SelectNStepUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class SelectNStepViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: SelectNStepUIState
    private lateinit var resultFlow: MutableStateFlow<Int?>

    fun start(): Flow<Int?> {
        val flow = MutableStateFlow<Int?>(null)
        resultFlow = flow
        return flow
    }

    fun done(result: Int) {
        close()
        resultFlow.value = result
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: SelectNStepEvent) {
        when (event) {
            SelectNStepEvent.Close -> close()
            is SelectNStepEvent.Select -> done(event.stepInd)
        }
    }

    suspend fun launchAndGet(stepCount:Int, use: (Int) -> Unit) {
        state = SelectNStepUIState(stepCount)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
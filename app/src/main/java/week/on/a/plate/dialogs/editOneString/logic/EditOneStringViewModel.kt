package week.on.a.plate.dialogs.editOneString.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editOneString.event.EditOneStringEvent
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class EditOneStringViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: EditOneStringUIState
    private lateinit var resultFlow: MutableStateFlow<String?>

    fun start(): Flow<String?> {
        val flow = MutableStateFlow<String?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = state.text.value
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: EditOneStringEvent) {
        when (event) {
            EditOneStringEvent.Close -> close()
            EditOneStringEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(
        oldData: EditOneStringUIState,
        use: suspend (String) -> Unit,
    ) {
        state = oldData
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
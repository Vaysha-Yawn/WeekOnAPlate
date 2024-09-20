package week.on.a.plate.core.dialogs.menu.datePicker.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.menu.datePicker.event.DatePickerEvent
import week.on.a.plate.core.dialogs.menu.datePicker.state.DatePickerUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.core.tools.dateToLocalDate
import java.time.LocalDate


class DatePickerViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: DatePickerUIState
    private lateinit var resultFlow: MutableStateFlow<LocalDate?>

    @OptIn(ExperimentalMaterial3Api::class)
    fun getLocalDate(state: DatePickerUIState): LocalDate? {
        return state.state.selectedDateMillis?.dateToLocalDate()
    }

    fun start(): Flow<LocalDate?> {
        val flow = MutableStateFlow<LocalDate?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        val date = getLocalDate(state)
        resultFlow.value = date
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: DatePickerEvent) {
        when (event) {
            DatePickerEvent.Close -> close()
            DatePickerEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(use: (LocalDate) -> Unit) {
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
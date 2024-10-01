package week.on.a.plate.dialogDatePicker.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.dialogDatePicker.event.DatePickerEvent
import week.on.a.plate.dialogDatePicker.state.DatePickerUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.core.utils.dateToLocalDate
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
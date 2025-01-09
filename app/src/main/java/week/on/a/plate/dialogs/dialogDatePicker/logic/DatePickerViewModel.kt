package week.on.a.plate.dialogs.dialogDatePicker.logic

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.core.utils.dateToLocalDate
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.dialogDatePicker.event.DatePickerEvent
import week.on.a.plate.dialogs.dialogDatePicker.state.DatePickerUIState
import week.on.a.plate.mainActivity.logic.MainViewModel
import java.time.LocalDate
import java.util.Locale


class DatePickerViewModel(
    locale: Locale,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (LocalDate?) -> Unit
) : DialogViewModel<LocalDate?>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    @OptIn(ExperimentalMaterial3Api::class)
    var state: DatePickerUIState = DatePickerUIState(DatePickerState(locale))

    @OptIn(ExperimentalMaterial3Api::class)
    fun getLocalDate(state: DatePickerUIState): LocalDate? {
        return state.state.selectedDateMillis?.dateToLocalDate()
    }

    fun onEvent(event: DatePickerEvent) {
        when (event) {
            DatePickerEvent.Close -> close()
            DatePickerEvent.Done -> done(getLocalDate(state))
        }
    }

    companion object {
        fun launch(
            mainViewModel: MainViewModel, useResult: (LocalDate?) -> Unit
        ) {
            DatePickerViewModel(
                mainViewModel.locale,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
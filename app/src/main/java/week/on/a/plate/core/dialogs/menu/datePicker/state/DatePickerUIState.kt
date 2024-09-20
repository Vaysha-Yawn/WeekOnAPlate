package week.on.a.plate.core.dialogs.menu.datePicker.state

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class DatePickerUIState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val state: DatePickerState,
){
    val show: MutableState<Boolean> = mutableStateOf(true)
}



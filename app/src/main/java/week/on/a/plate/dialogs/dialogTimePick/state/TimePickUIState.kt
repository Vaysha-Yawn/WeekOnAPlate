package week.on.a.plate.dialogs.dialogTimePick.state

import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

@OptIn(ExperimentalMaterial3Api::class)
class TimePickUIState(){
    val title: MutableState<String> = mutableStateOf("")
    val show: MutableState<Boolean> = mutableStateOf(true)
    val mode =  mutableStateOf(DisplayMode.Picker)
    val timeState: TimePickerState = TimePickerState(
        initialHour = 0,
        initialMinute = 0,
        true
    )
}



package week.on.a.plate.screenCreateRecipe.timePickDialog.state

import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
class TimePickUIState(
){
    val show: MutableState<Boolean> = mutableStateOf(true)
    val mode =  mutableStateOf(DisplayMode.Picker)
    val timeState: TimePickerState = TimePickerState(
        initialHour = 0,
        initialMinute = 0,
        true
    )

}



package week.on.a.plate.screenDeleteApply.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

class DeleteApplyUIState() {
    val message:MutableState<String> = mutableStateOf("")
}
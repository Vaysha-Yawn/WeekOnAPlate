package week.on.a.plate.screens.additional.specifyRecipeToCookPlan.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate
import java.time.LocalTime

data class SpecifyRecipeToCookPlanUIState(
   val date:MutableState<LocalDate> = mutableStateOf(LocalDate.now()),
    val time:MutableState<LocalTime> = mutableStateOf(LocalTime.of(0,0)),
    val isStart:MutableState<Boolean> = mutableStateOf(true),
)

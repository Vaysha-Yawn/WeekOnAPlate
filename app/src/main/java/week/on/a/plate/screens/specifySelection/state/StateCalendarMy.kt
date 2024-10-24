package week.on.a.plate.screens.specifySelection.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import java.time.LocalDate

data class StateCalendarMy(
    val activeDate: MutableState<LocalDate>,
    val allMonthDayAndIsPlanned: MutableState<List<Pair<LocalDate, Boolean>>>
) {
    val currentYear = mutableIntStateOf(activeDate.value.year)
    val currentMonth = mutableIntStateOf(activeDate.value.monthValue)
}
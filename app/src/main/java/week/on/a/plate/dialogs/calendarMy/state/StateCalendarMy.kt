package week.on.a.plate.dialogs.calendarMy.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import java.time.DayOfWeek
import java.time.LocalDate

data class StateCalendarMy(
    val activeDate: MutableState<LocalDate>,
    val allMonthDayAndIsPlanned: MutableState<List<Pair<LocalDate, Boolean>>>,
    val firstRow: MutableState<List<DayOfWeek>>
) {
    val currentYear = mutableIntStateOf(activeDate.value.year)
    val currentMonth = mutableIntStateOf(activeDate.value.monthValue)

    companion object {
        val emptyState = StateCalendarMy(activeDate = mutableStateOf(LocalDate.now()), mutableStateOf(
            listOf()), mutableStateOf(listOf()))
    }
}
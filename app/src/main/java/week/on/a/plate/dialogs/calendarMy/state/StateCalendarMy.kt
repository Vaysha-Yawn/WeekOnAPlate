package week.on.a.plate.dialogs.calendarMy.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate

data class StateCalendarMy(
    val activeDate: MutableState<LocalDate>,
    val allMonthDayAndIsPlanned: MutableState<List<Pair<LocalDate, Boolean>>>,
    var firstRow: MutableState<List<String>>
) {
    val currentYear = mutableIntStateOf(activeDate.value.year)
    val currentMonth = mutableIntStateOf(activeDate.value.monthValue)

    companion object {
        val emptyState = StateCalendarMy(activeDate = mutableStateOf(LocalDate.now()), mutableStateOf(
            listOf()), mutableStateOf(listOf()))
    }
}
package week.on.a.plate.screens.specifySelection.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.data.repository.tables.menu.selection.WeekRepository
import week.on.a.plate.screens.specifySelection.state.StateCalendarMy
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import javax.inject.Inject

class CalendarMyUseCase @Inject constructor(
    val repository: WeekRepository
) {
    private suspend fun getAllMonthDays(year: Int, month: Int,): List<Pair<LocalDate, Boolean>> {
        val yearV = YearMonth.of(year, month)
        val allList = (1..yearV.lengthOfMonth()).map { day ->
            LocalDate.of(year, month, day)
        }
        return allList.map {
            val sels = repository.getSelectionsByDate(it)
            Pair(it, sels.isNotEmpty())
        }
    }

    fun getFirstRow(locale: java.util.Locale): List<String> {
        return DayOfWeek.entries.map { it.getDisplayName(TextStyle.SHORT, locale) }
    }

    private suspend fun updateMonthValue(state: StateCalendarMy) {
        state.allMonthDayAndIsPlanned.value = getAllMonthDays(state.currentYear.intValue, state.currentMonth.intValue)
    }

    fun nextMonth(state: StateCalendarMy) {
        if (state.currentMonth.intValue == 12) {
            state.currentMonth.intValue = 1
            state.currentYear.intValue += 1
        } else {
            state.currentMonth.intValue += 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            updateMonthValue(state)
        }
    }

    fun lastMonth(state: StateCalendarMy) {
        if (state.currentMonth.intValue == 1) {
            state.currentMonth.intValue = 12
            state.currentYear.intValue -= 1
        } else {
            state.currentMonth.intValue -= 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            updateMonthValue(state)
        }
    }

    fun changeActiveDate(date: LocalDate, state: StateCalendarMy) {
        state.activeDate.value = date
    }
}
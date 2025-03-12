package week.on.a.plate.dialogs.calendarMy.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.data.repository.room.menu.selection.getDaysOfWeek
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

class CalendarMyUseCase @Inject constructor(
    private val repository: WeekMenuRepository,
    private val cookRepository: CookPlannerStepRepository,
) {
    suspend fun getAllMonthDays(year: Int, month: Int, isForMenu:Boolean): List<Pair<LocalDate, Boolean>> {
        val yearV = YearMonth.of(year, month)
        val allList = (1..yearV.lengthOfMonth()).map { day ->
            LocalDate.of(year, month, day)
        }
        return allList.map { localDate ->
            if (isForMenu){
                val selsIsNotEmpty = repository.getSelectionsByDate(localDate).map { it.positions.isNotEmpty() }.contains(true)
                Pair(localDate, selsIsNotEmpty)
            }else{
                val steps = cookRepository.getAllByDateNoFlow(localDate)
                Pair(localDate, steps.isNotEmpty())
            }
        }
    }

    fun getFirstRow(locale: java.util.Locale): List<DayOfWeek> {
        return getDaysOfWeek(LocalDate.now(), locale).map { it.dayOfWeek }
    }

    suspend fun updateMonthValue(state: StateCalendarMy, isForMenu:Boolean) {
        state.allMonthDayAndIsPlanned.value = getAllMonthDays(state.currentYear.intValue, state.currentMonth.intValue, isForMenu)
    }

    private fun nextMonth(state: StateCalendarMy, isForMenu:Boolean) {
        if (state.currentMonth.intValue == 12) {
            state.currentMonth.intValue = 1
            state.currentYear.intValue += 1
        } else {
            state.currentMonth.intValue += 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            updateMonthValue(state, isForMenu)
        }
    }

    private fun lastMonth(state: StateCalendarMy, isForMenu:Boolean) {
        if (state.currentMonth.intValue == 1) {
            state.currentMonth.intValue = 12
            state.currentYear.intValue -= 1
        } else {
            state.currentMonth.intValue -= 1
        }
        CoroutineScope(Dispatchers.IO).launch {
            updateMonthValue(state, isForMenu)
        }
    }

    private fun changeActiveDate(date: LocalDate, state: StateCalendarMy) {
        state.activeDate.value = date
    }

    fun onEvent(event: CalendarMyEvent, stateCalendar: StateCalendarMy, isForMenu:Boolean) {
        when(event){
            is CalendarMyEvent.ChangeActiveDate -> changeActiveDate(event.date,stateCalendar )
            CalendarMyEvent.LastMonth -> lastMonth(stateCalendar, isForMenu)
            CalendarMyEvent.NextMonth -> nextMonth(stateCalendar, isForMenu)
        }
    }
}
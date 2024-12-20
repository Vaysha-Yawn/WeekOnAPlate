package week.on.a.plate.dialogs.calendarMy.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class CalendarMyEvent : Event() {
    data object NextMonth : CalendarMyEvent()
    data object LastMonth : CalendarMyEvent()
    data class ChangeActiveDate(val date:LocalDate) : CalendarMyEvent()
}
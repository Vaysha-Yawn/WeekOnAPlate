package week.on.a.plate.dialogs.calendarMy.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed interface CalendarMyEvent : Event {
    object NextMonth : CalendarMyEvent
    object LastMonth : CalendarMyEvent
    class ChangeActiveDate(val date: LocalDate) : CalendarMyEvent
}
package week.on.a.plate.dialogs.timePick.event


import week.on.a.plate.core.Event

sealed interface TimePickEvent : Event {
    object Done : TimePickEvent
    object Close : TimePickEvent
}
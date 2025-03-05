package week.on.a.plate.dialogs.timePick.event


import week.on.a.plate.core.Event

sealed class TimePickEvent: Event() {
    data object Done: TimePickEvent()
    data object Close: TimePickEvent()
}
package week.on.a.plate.dialogTimePick.event


import week.on.a.plate.core.Event

sealed class TimePickEvent: Event() {
    data object Done: TimePickEvent()
    data object Close: TimePickEvent()
}
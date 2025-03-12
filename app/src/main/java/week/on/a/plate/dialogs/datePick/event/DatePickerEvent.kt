package week.on.a.plate.dialogs.datePick.event

import week.on.a.plate.core.Event

sealed interface DatePickerEvent : Event {
    object Done : DatePickerEvent
    object Close : DatePickerEvent
}
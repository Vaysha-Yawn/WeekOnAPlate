package week.on.a.plate.dialogs.datePick.event

import week.on.a.plate.core.Event

sealed class DatePickerEvent: Event() {
    data object Done: DatePickerEvent()
    data object Close: DatePickerEvent()
}
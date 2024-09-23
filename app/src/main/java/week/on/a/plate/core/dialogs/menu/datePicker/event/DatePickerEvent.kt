package week.on.a.plate.core.dialogs.menu.datePicker.event

import week.on.a.plate.core.Event

sealed class DatePickerEvent: Event() {
    data object Done: DatePickerEvent()
    data object Close: DatePickerEvent()
}
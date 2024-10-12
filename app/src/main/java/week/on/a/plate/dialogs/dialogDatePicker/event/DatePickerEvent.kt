package week.on.a.plate.dialogs.dialogDatePicker.event

import week.on.a.plate.core.Event

sealed class DatePickerEvent: Event() {
    data object Done: DatePickerEvent()
    data object Close: DatePickerEvent()
}
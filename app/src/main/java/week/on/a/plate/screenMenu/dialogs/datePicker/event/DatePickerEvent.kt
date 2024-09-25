package week.on.a.plate.screenMenu.dialogs.datePicker.event

import week.on.a.plate.core.Event

sealed class DatePickerEvent: Event() {
    data object Done: DatePickerEvent()
    data object Close: DatePickerEvent()
}
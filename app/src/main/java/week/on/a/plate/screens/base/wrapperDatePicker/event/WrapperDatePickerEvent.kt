package week.on.a.plate.screens.base.wrapperDatePicker.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed interface WrapperDatePickerEvent : Event {
    object SwitchWeekOrDayView : WrapperDatePickerEvent
    object ChooseWeek : WrapperDatePickerEvent
    object SwitchEditMode : WrapperDatePickerEvent
    class ChangeWeek(val date: LocalDate) : WrapperDatePickerEvent
}
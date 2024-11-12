package week.on.a.plate.screens.wrapperDatePicker.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class WrapperDatePickerEvent:Event() {
    data object SwitchWeekOrDayView : WrapperDatePickerEvent()
    data object ChooseWeek: WrapperDatePickerEvent()
    data object SwitchEditMode : WrapperDatePickerEvent()
    data class ChangeWeek(val date: LocalDate) : WrapperDatePickerEvent()
}
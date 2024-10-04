package week.on.a.plate.screenSpecifySelection.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class SpecifySelectionEvent : Event() {
    data class UpdatePreview(val date:LocalDate?) : SpecifySelectionEvent()
    data object AddCustomSelection : SpecifySelectionEvent()
    data object Done : SpecifySelectionEvent()
    data object Back : SpecifySelectionEvent()
    data object ChooseDate : SpecifySelectionEvent()
    data class ApplyDate(val date:LocalDate?) : SpecifySelectionEvent()
}
package week.on.a.plate.screens.additional.specifySelection.event

import android.content.Context
import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class SpecifySelectionEvent : Event() {
    data class UpdatePreview(val date:LocalDate) : SpecifySelectionEvent()
    data object AddCustomSelection : SpecifySelectionEvent()
    data class Done(val context: Context) : SpecifySelectionEvent()
    data object Back : SpecifySelectionEvent()
    data class ApplyDate(val date:LocalDate) : SpecifySelectionEvent()
    data class UpdateSelections (val context: Context): SpecifySelectionEvent()
}
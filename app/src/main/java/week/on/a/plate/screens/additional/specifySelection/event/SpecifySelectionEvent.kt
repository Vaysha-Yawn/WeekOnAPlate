package week.on.a.plate.screens.additional.specifySelection.event

import android.content.Context
import week.on.a.plate.core.Event
import java.time.LocalDate

sealed interface SpecifySelectionEvent : Event {
    class UpdatePreview(val date: LocalDate) : SpecifySelectionEvent
    object AddCustomSelection : SpecifySelectionEvent
    class Done(val context: Context) : SpecifySelectionEvent
    object Back : SpecifySelectionEvent
    class ApplyDate(val date: LocalDate) : SpecifySelectionEvent
    class UpdateSelections(val context: Context) : SpecifySelectionEvent
}
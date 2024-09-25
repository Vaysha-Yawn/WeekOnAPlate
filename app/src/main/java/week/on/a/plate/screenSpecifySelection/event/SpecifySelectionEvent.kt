package week.on.a.plate.screenSpecifySelection.event

import week.on.a.plate.core.Event

sealed class SpecifySelectionEvent : Event() {
    data object Done : SpecifySelectionEvent()
    data object Back : SpecifySelectionEvent()
    data object ChooseDate : SpecifySelectionEvent()
}
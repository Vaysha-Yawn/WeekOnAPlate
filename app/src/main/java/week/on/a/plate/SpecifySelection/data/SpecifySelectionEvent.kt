package week.on.a.plate.SpecifySelection.data

import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class SpecifySelectionEvent : Event() {
    data object Done : SpecifySelectionEvent()
    data object Back : SpecifySelectionEvent()
    data object ChooseDate : SpecifySelectionEvent()
}
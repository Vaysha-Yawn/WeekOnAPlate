package week.on.a.plate.dialogs.editSelection.event

import week.on.a.plate.core.Event

sealed class EditSelectionEvent: Event() {
    data object Done: EditSelectionEvent()
    data object Close: EditSelectionEvent()
    data object ChooseTime: EditSelectionEvent()
}
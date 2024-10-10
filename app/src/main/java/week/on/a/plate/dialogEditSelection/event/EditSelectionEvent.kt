package week.on.a.plate.dialogEditSelection.event

import week.on.a.plate.core.Event

sealed class EditSelectionEvent: Event() {
    data object Done: EditSelectionEvent()
    data object Close: EditSelectionEvent()
    data object ChooseTime: EditSelectionEvent()
}
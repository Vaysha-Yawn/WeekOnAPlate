package week.on.a.plate.dialogEditOtherPosition.event

import week.on.a.plate.core.Event

sealed class EditOtherPositionEvent: Event() {
    data object Edit: EditOtherPositionEvent()
    data object Double: EditOtherPositionEvent()
    data object Delete: EditOtherPositionEvent()
    data object Move: EditOtherPositionEvent()
    data object Close: EditOtherPositionEvent()
}
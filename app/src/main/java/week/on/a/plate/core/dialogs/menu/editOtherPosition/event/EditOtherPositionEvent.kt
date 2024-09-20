package week.on.a.plate.core.dialogs.menu.editOtherPosition.event

import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class EditOtherPositionEvent:Event() {
    data object Edit: EditOtherPositionEvent()
    data object Double: EditOtherPositionEvent()
    data object Delete: EditOtherPositionEvent()
    data object Move: EditOtherPositionEvent()
    data object Close: EditOtherPositionEvent()
}
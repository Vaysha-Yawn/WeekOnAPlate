package week.on.a.plate.core.dialogs.editRecipePosition.event

import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class EditRecipePositionEvent:Event() {
    data object AddToCart: EditRecipePositionEvent()
    data object Double: EditRecipePositionEvent()
    data object Delete: EditRecipePositionEvent()
    data object FindReplace: EditRecipePositionEvent()
    data object ChangePotionsCount: EditRecipePositionEvent()
    data object Move: EditRecipePositionEvent()
    data object Close: EditRecipePositionEvent()
}
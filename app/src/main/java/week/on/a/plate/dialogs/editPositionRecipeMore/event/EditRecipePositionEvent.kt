package week.on.a.plate.dialogs.editPositionRecipeMore.event

import week.on.a.plate.core.Event

sealed class EditRecipePositionEvent: Event() {
    data object AddToCart: EditRecipePositionEvent()
    data object Double: EditRecipePositionEvent()
    data object Delete: EditRecipePositionEvent()
    data object FindReplace: EditRecipePositionEvent()
    data object ChangePotionsCount: EditRecipePositionEvent()
    data object Move: EditRecipePositionEvent()
    data object Close: EditRecipePositionEvent()
    data object CookPlan : EditRecipePositionEvent()
}
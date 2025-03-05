package week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.event

import week.on.a.plate.core.Event

sealed class ActionMoreRecipePositionEvent: Event() {
    data object AddToCart: ActionMoreRecipePositionEvent()
    data object Double: ActionMoreRecipePositionEvent()
    data object Delete: ActionMoreRecipePositionEvent()
    data object FindReplace: ActionMoreRecipePositionEvent()
    data object ChangePotionsCount: ActionMoreRecipePositionEvent()
    data object Move: ActionMoreRecipePositionEvent()
    data object Close: ActionMoreRecipePositionEvent()
    data object CookPlan : ActionMoreRecipePositionEvent()
}
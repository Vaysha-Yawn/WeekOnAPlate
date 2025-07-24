package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.event

import week.on.a.plate.core.Event

sealed interface ActionMoreRecipePositionEvent : Event {
    object AddToCart : ActionMoreRecipePositionEvent
    object Delete : ActionMoreRecipePositionEvent
    object ChangePotionsCount : ActionMoreRecipePositionEvent
    object Move : ActionMoreRecipePositionEvent
    object Close : ActionMoreRecipePositionEvent
    object CookPlan : ActionMoreRecipePositionEvent
}
package week.on.a.plate.dialogs.forMenuScreen.editPositionRecipeMore.event

import android.content.Context
import week.on.a.plate.core.Event

sealed class ActionMoreRecipePositionEvent: Event() {
    object AddToCart : ActionMoreRecipePositionEvent()
    object Double : ActionMoreRecipePositionEvent()
    object Delete : ActionMoreRecipePositionEvent()
    class FindReplace(val context: Context) : ActionMoreRecipePositionEvent()
    object ChangePotionsCount : ActionMoreRecipePositionEvent()
    object Move : ActionMoreRecipePositionEvent()
    object Close : ActionMoreRecipePositionEvent()
    object CookPlan : ActionMoreRecipePositionEvent()
}
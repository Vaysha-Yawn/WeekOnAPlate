package week.on.a.plate.screens.shoppingList.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

sealed class ShoppingListEvent:Event() {
    data object Add : ShoppingListEvent()
    data object DeleteChecked : ShoppingListEvent()
    data class Check(val position: IngredientInRecipeView) : ShoppingListEvent()
    data class Uncheck(val position: IngredientInRecipeView) : ShoppingListEvent()
    data class Edit(val ingredient: IngredientInRecipeView) : ShoppingListEvent()
    class Share(val context: Context) : ShoppingListEvent()
}
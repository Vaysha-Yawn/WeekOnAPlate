package week.on.a.plate.screens.base.shoppingList.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

sealed interface ShoppingListEvent : Event {
    object Add : ShoppingListEvent
    object DeleteChecked : ShoppingListEvent
    class DeleteAll(val context: Context) : ShoppingListEvent
    class Check(val position: IngredientInRecipeView) : ShoppingListEvent
    class Uncheck(val position: IngredientInRecipeView) : ShoppingListEvent
    class Edit(val ingredient: IngredientInRecipeView) : ShoppingListEvent
    class Share(val context: Context) : ShoppingListEvent
}
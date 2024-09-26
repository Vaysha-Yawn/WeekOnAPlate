package week.on.a.plate.screenShoppingList.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

class ShoppingListUIState {
    val listUnchecked = mutableStateOf(listOf<IngredientInRecipeView>())
    val listChecked = mutableStateOf(listOf<IngredientInRecipeView>())
}

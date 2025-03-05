package week.on.a.plate.screens.base.shoppingList.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

class ShoppingListUIState {
    var listUnchecked:State<List<IngredientInRecipeView>> = mutableStateOf(listOf<IngredientInRecipeView>())
    var listChecked:State<List<IngredientInRecipeView>> = mutableStateOf(listOf<IngredientInRecipeView>())
}

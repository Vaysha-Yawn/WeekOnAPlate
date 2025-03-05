package week.on.a.plate.screens.additional.inventory.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView

class InventoryUIState() {
    val list: MutableState<List<InventoryPositionData>> = mutableStateOf(listOf())
}

data class InventoryPositionData(
    val ingredient: IngredientView,
    val countTarget: Int,
) {
    val answer: MutableState<Boolean> = mutableStateOf(true)
    companion object {
        fun getByIngredientInRecipe(ingredient: IngredientInRecipeView): InventoryPositionData {
            return InventoryPositionData(
                ingredient.ingredientView,
                ingredient.count,
            )
        }
    }
}
package week.on.a.plate.screenInventory.state

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
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
    //0 = нет
    //countTarget == answer = есть
    // среднее есть только
    val answer: MutableIntState = mutableIntStateOf(0)
    companion object {
        fun getByIngredientInRecipe(ingredient: IngredientInRecipeView): InventoryPositionData {
            return InventoryPositionData(
                ingredient.ingredientView,
                ingredient.count,
            )
        }
    }
}
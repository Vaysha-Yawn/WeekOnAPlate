package week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


class IngredientInRecipeMapper() {
    fun IngredientInRecipeRoom.roomToView(ingredientView: IngredientView): IngredientInRecipeView =
        IngredientInRecipeView(
            id = id,
            ingredientView = ingredientView,
            description = description,
            count = count.toInt(),
        )

    fun IngredientInRecipeView.viewToRoom(recipeId:Long, ingredientId:Long,): IngredientInRecipeRoom =
        IngredientInRecipeRoom(
            recipeId = recipeId,
            ingredientId = ingredientId,
            description = description,
            count = count.toDouble()
        )
}

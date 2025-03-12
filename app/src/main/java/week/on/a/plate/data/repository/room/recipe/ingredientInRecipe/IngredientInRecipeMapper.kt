package week.on.a.plate.data.repository.room.recipe.ingredientInRecipe

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.IngredientView


class IngredientInRecipeMapper() {
    fun IngredientInRecipeRoom.roomToView(ingredientView: IngredientView): IngredientInRecipeView =
        IngredientInRecipeView(
            id = id,
            ingredientView = ingredientView,
            description = description,
            count = count.toInt(),
        )

    fun IngredientInRecipeView.viewToRoom(recipeId: Long): IngredientInRecipeRoom =
        IngredientInRecipeRoom(
            recipeId = recipeId,
            ingredientId = ingredientView.ingredientId,
            description = description,
            count = count.toDouble()
        )
}

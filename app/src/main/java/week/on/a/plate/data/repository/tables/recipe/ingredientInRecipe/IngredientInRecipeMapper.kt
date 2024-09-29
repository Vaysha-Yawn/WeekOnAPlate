package week.on.a.plate.data.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


class IngredientInRecipeMapper() {
    fun IngredientInRecipeRoom.roomToView(ingredientView: IngredientView): IngredientInRecipeView =
        IngredientInRecipeView(
            id = this.id,
            ingredientView = ingredientView,
            description = this.description,
            count = this.count.toInt(),
        )

    fun IngredientInRecipeView.viewToRoom(recipeId:Long, ingredientId:Long,): IngredientInRecipeRoom =
        IngredientInRecipeRoom(
            recipeId = recipeId,
            ingredientId = ingredientId,
            description = this.description,
            count = this.count.toDouble()
        )
}

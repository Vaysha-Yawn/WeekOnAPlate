package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.core.data.recipe.IngredientView


class IngredientInRecipeMapper() {
    fun IngredientInRecipeRoom.roomToView(ingredientView: IngredientView): week.on.a.plate.core.data.recipe.IngredientInRecipeView =
        week.on.a.plate.core.data.recipe.IngredientInRecipeView(
            id = this.id,
            ingredientView = ingredientView,
            description = this.description,
            count = this.count,
        )

    fun week.on.a.plate.core.data.recipe.IngredientInRecipeView.viewToRoom(recipeId:Long, ingredientId:Long,): IngredientInRecipeRoom =
        IngredientInRecipeRoom(
            recipeId = recipeId,
            ingredientId = ingredientId,
            description = this.description,
            count = this.count
        )
}

package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.core.data.recipe.IngredientView


class IngredientInRecipeMapper() {
    fun IngredientInRecipe.roomToView(ingredientView: IngredientView): week.on.a.plate.core.data.recipe.IngredientInRecipeView =
        week.on.a.plate.core.data.recipe.IngredientInRecipeView(
            ingredientView = ingredientView,
            count = this.count
        )

    fun week.on.a.plate.core.data.recipe.IngredientInRecipeView.viewToRoom(recipeId:Long): IngredientInRecipe =
        IngredientInRecipe(
            id = this.ingredientView.ingredientId,
            recipeId = recipeId,
            ingredientId = this.ingredientView.ingredientId,
            count = this.count
        )
}

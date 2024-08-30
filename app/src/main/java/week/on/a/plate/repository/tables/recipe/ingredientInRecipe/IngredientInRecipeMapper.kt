package week.on.a.plate.repository.tables.recipe.ingredientInRecipe

import week.on.a.plate.core.data.recipe.Ingredient


class IngredientInRecipeMapper() {
    fun IngredientInRecipe.roomToView(ingredient: Ingredient): week.on.a.plate.core.data.recipe.IngredientInRecipe =
        week.on.a.plate.core.data.recipe.IngredientInRecipe(
            ingredient = ingredient,
            count = this.count
        )

    fun week.on.a.plate.core.data.recipe.IngredientInRecipe.viewToRoom(recipeId:Long): IngredientInRecipe =
        IngredientInRecipe(
            id = this.ingredient.ingredientId,
            recipeId = recipeId,
            ingredientId = this.ingredient.ingredientId,
            count = this.count
        )
}

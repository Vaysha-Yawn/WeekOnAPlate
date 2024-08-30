package week.on.a.plate.repository.tables.recipe.ingredientCategory

import week.on.a.plate.core.data.recipe.Ingredient

class IngredientCategoryMapper() {
    fun IngredientCategory.roomToView(ingredients: List<Ingredient>): week.on.a.plate.core.data.recipe.IngredientCategory =
        week.on.a.plate.core.data.recipe.IngredientCategory(
             id = this.ingredientCategoryId,
             name = this.name,
             ingredients = ingredients
        )

    fun week.on.a.plate.core.data.recipe.IngredientCategory.viewToRoom(): IngredientCategory =
        IngredientCategory(
             ingredientCategoryId = this.id,
             name = this.name
        )
}

package week.on.a.plate.repository.tables.recipe.ingredientCategory

import week.on.a.plate.core.data.recipe.IngredientView

class IngredientCategoryMapper() {
    fun IngredientCategory.roomToView(ingredientViews: List<IngredientView>): week.on.a.plate.core.data.recipe.IngredientCategory =
        week.on.a.plate.core.data.recipe.IngredientCategory(
             id = this.ingredientCategoryId,
             name = this.name,
             ingredientViews = ingredientViews
        )

    fun week.on.a.plate.core.data.recipe.IngredientCategory.viewToRoom(): IngredientCategory =
        IngredientCategory(
             ingredientCategoryId = this.id,
             name = this.name
        )
}

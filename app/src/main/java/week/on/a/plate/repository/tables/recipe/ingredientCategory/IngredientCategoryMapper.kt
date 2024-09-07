package week.on.a.plate.repository.tables.recipe.ingredientCategory

import week.on.a.plate.core.data.recipe.IngredientView

class IngredientCategoryMapper() {
    fun IngredientCategoryRoom.roomToView(ingredientViews: List<IngredientView>): week.on.a.plate.core.data.recipe.IngredientCategoryView =
        week.on.a.plate.core.data.recipe.IngredientCategoryView(
             id = this.ingredientCategoryId,
             name = this.name,
             ingredientViews = ingredientViews
        )

    fun week.on.a.plate.core.data.recipe.IngredientCategoryView.viewToRoom(): IngredientCategoryRoom =
        IngredientCategoryRoom(
             name = this.name
        )
}

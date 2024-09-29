package week.on.a.plate.data.repository.tables.filters.ingredientCategory

import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView

class IngredientCategoryMapper() {
    fun IngredientCategoryRoom.roomToView(ingredientViews: List<IngredientView>): IngredientCategoryView =
        IngredientCategoryView(
             id = this.ingredientCategoryId,
             name = this.name,
             ingredientViews = ingredientViews
        )

    fun IngredientCategoryView.viewToRoom(): IngredientCategoryRoom =
        IngredientCategoryRoom(
             name = this.name
        )
}

package week.on.a.plate.data.repository.room.filters.ingredientCategory

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView

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

package week.on.a.plate.data.repository.room.filters.ingredient

import week.on.a.plate.data.dataView.recipe.IngredientView

class IngredientMapper() {
    fun IngredientRoom.roomToView(): IngredientView =
        IngredientView(
            ingredientId = this.ingredientId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )

    fun IngredientView.viewToRoom(ingredientCategoryId: Long): IngredientRoom =
        IngredientRoom(
            ingredientCategoryId = ingredientCategoryId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )
}

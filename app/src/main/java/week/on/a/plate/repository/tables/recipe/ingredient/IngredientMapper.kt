package week.on.a.plate.repository.tables.recipe.ingredient

class IngredientMapper() {
    fun IngredientRoom.roomToView(): week.on.a.plate.core.data.recipe.IngredientView =
        week.on.a.plate.core.data.recipe.IngredientView(
            ingredientId = this.ingredientId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )

    fun week.on.a.plate.core.data.recipe.IngredientView.viewToRoom(ingredientCategoryId: Long): IngredientRoom =
        IngredientRoom(
            ingredientCategoryId = ingredientCategoryId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )
}

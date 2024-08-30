package week.on.a.plate.repository.tables.recipe.ingredient

class IngredientMapper() {
    fun Ingredient.roomToView(): week.on.a.plate.core.data.recipe.Ingredient =
        week.on.a.plate.core.data.recipe.Ingredient(
            ingredientId = this.ingredientId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )

    fun week.on.a.plate.core.data.recipe.Ingredient.viewToRoom(ingredientCategoryId: Long): Ingredient =
        Ingredient(
            ingredientId = this.ingredientId,
            ingredientCategoryId = ingredientCategoryId,
            img = this.img,
            name = this.name,
            measure = this.measure
        )
}

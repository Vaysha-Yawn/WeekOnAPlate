package week.on.a.plate.repository.tables.recipe.recipeTag


class RecipeTagMapper() {
    fun RecipeTag.roomToView(): week.on.a.plate.core.data.recipe.RecipeTagView =
        week.on.a.plate.core.data.recipe.RecipeTagView(
            id = this.recipeTagId,
            tagName = this.tagName,
            isTypeOfMeal = this.isTypeOfMeal,
        )

    fun week.on.a.plate.core.data.recipe.RecipeTagView.viewToRoom(recipeTagCategoryId:Long): RecipeTag =
        RecipeTag(
            recipeTagId = this.id,
            recipeTagCategoryId = recipeTagCategoryId,
            tagName = this.tagName,
            isTypeOfMeal = this.isTypeOfMeal,
        )
}

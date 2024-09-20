package week.on.a.plate.repository.tables.recipe.recipeTag


class RecipeTagMapper() {
    fun RecipeTagRoom.roomToView(): week.on.a.plate.core.data.recipe.RecipeTagView =
        week.on.a.plate.core.data.recipe.RecipeTagView(
            id = this.recipeTagId,
            tagName = this.tagName,
        )

    fun week.on.a.plate.core.data.recipe.RecipeTagView.viewToRoom(recipeTagCategoryId:Long): RecipeTagRoom =
        RecipeTagRoom(
            recipeTagCategoryId = recipeTagCategoryId,
            tagName = this.tagName,
        )
}

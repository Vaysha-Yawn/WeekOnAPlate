package week.on.a.plate.repository.tables.recipe.recipeTag


class RecipeTagMapper() {
    fun RecipeTag.roomToView(): week.on.a.plate.core.data.recipe.RecipeTag =
        week.on.a.plate.core.data.recipe.RecipeTag(
            id = this.recipeTagId,
            tagName = this.tagName
        )

    fun week.on.a.plate.core.data.recipe.RecipeTag.viewToRoom(recipeTagCategoryId:Long): RecipeTag =
        RecipeTag(
            recipeTagId = this.id,
            recipeTagCategoryId = recipeTagCategoryId,
            tagName = this.tagName
        )
}

package week.on.a.plate.data.repository.room.filters.recipeTag

import week.on.a.plate.data.dataView.recipe.RecipeTagView


class RecipeTagMapper() {
    fun RecipeTagRoom.roomToView(): RecipeTagView =
        RecipeTagView(
            id = this.recipeTagId,
            tagName = this.tagName,
        )

    fun RecipeTagView.viewToRoom(recipeTagCategoryId: Long): RecipeTagRoom =
        RecipeTagRoom(
            recipeTagCategoryId = recipeTagCategoryId,
            tagName = this.tagName,
        )
}

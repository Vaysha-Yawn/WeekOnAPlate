package week.on.a.plate.data.repository.room.filters.recipeTagCategory

import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView


class RecipeTagCategoryMapper() {
    fun RecipeTagCategoryRoom.roomToView(tags: List<RecipeTagView>): TagCategoryView =
        TagCategoryView(id = this.recipeTagCategoryId, name = this.name, tags = tags)

    fun TagCategoryView.viewToRoom(): RecipeTagCategoryRoom =
        RecipeTagCategoryRoom(name = this.name)
}

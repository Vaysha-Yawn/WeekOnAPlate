package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import week.on.a.plate.core.data.recipe.RecipeTagView


class RecipeTagCategoryMapper() {
    fun RecipeTagCategory.roomToView(tags: List<RecipeTagView>): week.on.a.plate.core.data.recipe.TagCategoryView =
        week.on.a.plate.core.data.recipe.TagCategoryView(
            id = this.recipeTagCategoryId,
            name = this.name,
            tags = tags
        )

    fun week.on.a.plate.core.data.recipe.TagCategoryView.viewToRoom(): RecipeTagCategory =
        RecipeTagCategory(
            recipeTagCategoryId = this.id,
            name = this.name
        )
}

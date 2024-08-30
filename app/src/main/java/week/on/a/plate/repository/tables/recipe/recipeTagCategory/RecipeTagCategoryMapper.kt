package week.on.a.plate.repository.tables.recipe.recipeTagCategory

import week.on.a.plate.core.data.recipe.RecipeTag


class RecipeTagCategoryMapper() {
    fun RecipeTagCategory.roomToView(tags: List<RecipeTag>): week.on.a.plate.core.data.recipe.TagCategory =
        week.on.a.plate.core.data.recipe.TagCategory(
            id = this.recipeTagCategoryId,
            name = this.name,
            tags = tags
        )

    fun week.on.a.plate.core.data.recipe.TagCategory.viewToRoom(): RecipeTagCategory =
        RecipeTagCategory(
            recipeTagCategoryId = this.id,
            name = this.name
        )
}

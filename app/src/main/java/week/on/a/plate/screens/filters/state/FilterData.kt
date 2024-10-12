package week.on.a.plate.screens.filters.state

import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

enum class FilterMode {
    Multiple, One
}

enum class FilterEnum {
    Ingredient, Tag, CategoryTag, CategoryIngredient, IngredientAndTag,
}

data class FilterResult(
    val tags:List<RecipeTagView>?,
    val ingredients:List<IngredientView>?,
    val tagsCategories:List<TagCategoryView>?,
    val ingredientsCategories:List<IngredientCategoryView>?,
)
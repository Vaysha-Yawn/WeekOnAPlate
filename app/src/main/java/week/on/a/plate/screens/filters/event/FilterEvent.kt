package week.on.a.plate.screens.filters.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView


sealed class FilterEvent : Event() {
    class SearchFilter(val text: String = "") : FilterEvent()
    data class VoiceSearchFilters(val context: Context) : FilterEvent()
    data object ClearSearch: FilterEvent()

    data object CreateActive: FilterEvent()
    data object CreateTag : FilterEvent()
    data object CreateIngredient : FilterEvent()
    data object CreateTagCategory : FilterEvent()
    data object CreateIngredientCategory : FilterEvent()

    data object Back : FilterEvent()
    data object Done : FilterEvent()

    data object SelectedFilters : FilterEvent()

    data class SelectTag(val tag: RecipeTagView) : FilterEvent()
    data class SelectIngredient(val ingredient: IngredientView) : FilterEvent()
    class SelectTagCategory(val tagCategoryView: TagCategoryView) : FilterEvent()
    class SelectIngredientCategory(val ingredientCategoryView: IngredientCategoryView) : FilterEvent()

    data class EditOrDeleteTag(val tag: RecipeTagView) : FilterEvent()
    data class EditOrDeleteIngredient(val ingredient: IngredientView) : FilterEvent()
    data class EditOrDeleteTagCategory(val tagCategory: TagCategoryView) : FilterEvent()
    data class EditOrDeleteIngredientCategory(val ingredientCategory: IngredientCategoryView) : FilterEvent()
}
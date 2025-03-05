package week.on.a.plate.screens.additional.filters.event

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

    data class CreateActive(val context:Context): FilterEvent()
    data class CreateTag(val context:Context) : FilterEvent()
    data class CreateIngredient(val context:Context) : FilterEvent()
    data class CreateTagCategory(val context:Context) : FilterEvent()
    data class CreateIngredientCategory(val context:Context) : FilterEvent()

    data object Back : FilterEvent()
    data object Done : FilterEvent()

    data object SelectedFilters : FilterEvent()

    data class SelectTag(val tag: RecipeTagView) : FilterEvent()
    data class SelectIngredient(val ingredient: IngredientView) : FilterEvent()
    data class SelectTagCategory(val tagCategoryView: TagCategoryView) : FilterEvent()
    data class SelectIngredientCategory(val ingredientCategoryView: IngredientCategoryView) : FilterEvent()

    data class EditOrDeleteTag(val context:Context, val tag: RecipeTagView) : FilterEvent()
    data class EditOrDeleteIngredient(val context:Context, val ingredient: IngredientView) : FilterEvent()
    data class EditOrDeleteTagCategory(val tagCategory: TagCategoryView, val context:Context) : FilterEvent()
    data class EditOrDeleteIngredientCategory(val ingredientCategory: IngredientCategoryView, val context:Context) : FilterEvent()
}
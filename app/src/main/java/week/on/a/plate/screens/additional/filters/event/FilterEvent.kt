package week.on.a.plate.screens.additional.filters.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView


sealed interface FilterEvent : Event {
    class SearchFilter(val text: String = "") : FilterEvent
    class VoiceSearchFilters(val context: Context) : FilterEvent
    object ClearSearch : FilterEvent

    class CreateActive(val context: Context) : FilterEvent
    class CreateTag(val context: Context) : FilterEvent
    class CreateIngredient(val context: Context) : FilterEvent
    class CreateTagCategory(val context: Context) : FilterEvent
    class CreateIngredientCategory(val context: Context) : FilterEvent

    object Back : FilterEvent
    object Done : FilterEvent

    object SelectedFilters : FilterEvent

    class SelectTag(val tag: RecipeTagView) : FilterEvent
    class SelectIngredient(val ingredient: IngredientView) : FilterEvent
    class SelectTagCategory(val tagCategoryView: TagCategoryView) : FilterEvent
    class SelectIngredientCategory(val ingredientCategoryView: IngredientCategoryView) : FilterEvent

    class EditOrDeleteTag(val context: Context, val tag: RecipeTagView) : FilterEvent
    class EditOrDeleteIngredient(val context: Context, val ingredient: IngredientView) : FilterEvent
    class EditOrDeleteTagCategory(val tagCategory: TagCategoryView, val context: Context) :
        FilterEvent

    class EditOrDeleteIngredientCategory(
        val ingredientCategory: IngredientCategoryView,
        val context: Context
    ) : FilterEvent
}
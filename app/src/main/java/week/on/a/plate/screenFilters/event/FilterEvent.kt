package week.on.a.plate.screenFilters.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

sealed class FilterEvent : Event() {
    class SearchFilter(val text: String = "") : FilterEvent()
    class CreateTag(val text: String) : FilterEvent()
    class CreateIngredient(val text: String) : FilterEvent()
    data object VoiceSearchFilters : FilterEvent()
    data object Back : FilterEvent()
    data object Done : FilterEvent()
    data object SelectedFilters : FilterEvent()
    data class SelectTag(val tag: RecipeTagView) : FilterEvent()
    data class SelectIngredient(val ingredient: IngredientView) : FilterEvent()
    data object ClearSearch: FilterEvent()
    data class EditOrDeleteTag(val tag: RecipeTagView) : FilterEvent()
    data class EditOrDeleteIngredient(val ingredient: IngredientView) : FilterEvent()
}
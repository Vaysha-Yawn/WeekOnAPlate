package week.on.a.plate.core.fullScereenDialog.filters.event

import week.on.a.plate.core.Event
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

sealed class FilterEvent : Event() {
    class SearchFilter(val text: String = "") : FilterEvent()
    class CreateTag(val text: String) : FilterEvent()
    class CreateIngredient(val text: String) : FilterEvent()
    data object VoiceSearchFilters : FilterEvent()
    data object Back : FilterEvent()
    data object SelectedFilters : FilterEvent()
    data class SelectTag(val tag: RecipeTagView) : FilterEvent()
    data class SelectIngredient(val ingredient: IngredientView) : FilterEvent()
    data object ClearSearch: FilterEvent()
}
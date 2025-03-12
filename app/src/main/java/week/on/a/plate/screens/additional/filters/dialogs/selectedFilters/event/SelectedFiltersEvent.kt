package week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.event


import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

sealed interface SelectedFiltersEvent : Event {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : SelectedFiltersEvent
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : SelectedFiltersEvent
    object Close : SelectedFiltersEvent
}
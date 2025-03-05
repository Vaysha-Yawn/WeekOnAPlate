package week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.event


import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.core.Event

sealed class SelectedFiltersEvent: Event() {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : SelectedFiltersEvent()
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : SelectedFiltersEvent()
    data object Close: SelectedFiltersEvent()
}
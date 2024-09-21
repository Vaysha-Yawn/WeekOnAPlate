package week.on.a.plate.core.dialogs.selectedFilters.event


import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class SelectedFiltersEvent:Event() {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : SelectedFiltersEvent()
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : SelectedFiltersEvent()
    data object Close: SelectedFiltersEvent()
}
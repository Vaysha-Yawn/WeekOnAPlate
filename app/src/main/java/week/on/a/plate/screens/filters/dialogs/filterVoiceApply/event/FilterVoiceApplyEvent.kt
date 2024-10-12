package week.on.a.plate.screens.filters.dialogs.filterVoiceApply.event


import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.core.Event

sealed class FilterVoiceApplyEvent: Event() {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : FilterVoiceApplyEvent()
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : FilterVoiceApplyEvent()
    data object Close: FilterVoiceApplyEvent()
    data object Done: FilterVoiceApplyEvent()
}
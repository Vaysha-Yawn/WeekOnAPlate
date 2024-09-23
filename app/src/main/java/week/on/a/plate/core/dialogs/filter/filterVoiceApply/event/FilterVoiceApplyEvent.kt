package week.on.a.plate.core.dialogs.filter.filterVoiceApply.event


import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.Event

sealed class FilterVoiceApplyEvent: Event() {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : FilterVoiceApplyEvent()
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : FilterVoiceApplyEvent()
    data object Close: FilterVoiceApplyEvent()
    data object Done: FilterVoiceApplyEvent()
}
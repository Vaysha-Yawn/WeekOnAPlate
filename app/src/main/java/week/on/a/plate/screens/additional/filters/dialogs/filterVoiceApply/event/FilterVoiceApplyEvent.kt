package week.on.a.plate.screens.additional.filters.dialogs.filterVoiceApply.event


import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

sealed interface FilterVoiceApplyEvent : Event {
    class RemoveSelectedTag(val recipeTagView: RecipeTagView) : FilterVoiceApplyEvent
    class RemoveSelectedIngredient(val ingredientView: IngredientView) : FilterVoiceApplyEvent
    object Close : FilterVoiceApplyEvent
    object Done : FilterVoiceApplyEvent
}
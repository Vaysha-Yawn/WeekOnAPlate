package week.on.a.plate.filters

import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class FilterEvent : Event(){
    class SearchFilter(val text: String = "", val inIngredients:Boolean) : FilterEvent()
    class RemoveSelectedTag(val tag: RecipeTagView) : FilterEvent()
    class RemoveSelectedTagVoiceApply(val tag: RecipeTagView) : FilterEvent()
    class RemoveSelectedIngredientVoiceApply(val ingredient: IngredientView) : FilterEvent()
    class RemoveSelectedIngredient(val ingredient: IngredientView) : FilterEvent()
    data object VoiceApply : FilterEvent()
    data object VoiceSearch : FilterEvent()
    data object VoiceSearchFilters : FilterEvent()
    data object ClearResultSearch : FilterEvent()
}
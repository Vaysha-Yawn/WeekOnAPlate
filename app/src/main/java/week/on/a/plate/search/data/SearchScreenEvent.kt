package week.on.a.plate.search.data

import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class SearchScreenEvent : Event(){
    class Search(val text: String = "", val filters:List<RecipeTagView> = listOf(), val ingredients:List<IngredientView> = listOf()) : SearchScreenEvent()
    class FlipFavorite(val id: Long, val inFavorite: Boolean) : SearchScreenEvent()
    data object VoiceSearch : SearchScreenEvent()
    data object ClearResultSearch : SearchScreenEvent()
}
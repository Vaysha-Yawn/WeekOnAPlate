package week.on.a.plate.search.event

import week.on.a.plate.core.Event
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.RecipeView

sealed class SearchScreenEvent : Event(){
    data object Search : SearchScreenEvent()
    class FlipFavorite(val id: Long, val inFavorite: Boolean) : SearchScreenEvent()
    data object VoiceSearch : SearchScreenEvent()
    data object Back : SearchScreenEvent()
    class AddToMenu(val recipeView: RecipeView): SearchScreenEvent()
    class NavigateToFullRecipe(val recipeView: RecipeView): SearchScreenEvent()
    class SelectTag(val recipeTagView: RecipeTagView) : SearchScreenEvent()
    data object ToFilter : SearchScreenEvent()
    data object CreateRecipe : SearchScreenEvent()
    data object SearchInWeb : SearchScreenEvent()
}
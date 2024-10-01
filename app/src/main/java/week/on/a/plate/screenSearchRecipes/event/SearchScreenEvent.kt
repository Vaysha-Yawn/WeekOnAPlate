package week.on.a.plate.screenSearchRecipes.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView

sealed class SearchScreenEvent : Event(){
    data object Search : SearchScreenEvent()
    class FlipFavorite(val recipe: RecipeView, val inFavorite: Boolean) : SearchScreenEvent()
    data object VoiceSearch : SearchScreenEvent()
    data object Back : SearchScreenEvent()
    class AddToMenu(val recipeView: RecipeView): SearchScreenEvent()
    class NavigateToFullRecipe(val recipeView: RecipeView): SearchScreenEvent()
    class SelectTag(val recipeTagView: RecipeTagView) : SearchScreenEvent()
    data object ToFilter : SearchScreenEvent()
    data object CreateRecipe : SearchScreenEvent()
    data object Clear : SearchScreenEvent()
    data object SearchFavorite : SearchScreenEvent()
    data object SearchAll : SearchScreenEvent()
    data object SearchRandom : SearchScreenEvent()
}
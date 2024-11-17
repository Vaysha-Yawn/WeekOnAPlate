package week.on.a.plate.screens.searchRecipes.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.searchRecipes.state.ResultSortType
import week.on.a.plate.screens.searchRecipes.state.ResultSortingDirection

sealed class SearchScreenEvent : Event(){
    data object Search : SearchScreenEvent()
    class FlipFavorite(val recipe: RecipeView, val inFavorite: Boolean) : SearchScreenEvent()
    data class VoiceSearch(val context: Context) : SearchScreenEvent()
    data object Back : SearchScreenEvent()
    class AddToMenu(val recipeView: RecipeView): SearchScreenEvent()
    class NavigateToFullRecipe(val recipeView: RecipeView): SearchScreenEvent()
    class SelectTag(val recipeTagView: RecipeTagView) : SearchScreenEvent()
    class ChangeSort(val type : ResultSortType, val direction: ResultSortingDirection) : SearchScreenEvent()
    data object ToFilter : SearchScreenEvent()
    data object CreateRecipe : SearchScreenEvent()
    data object Clear : SearchScreenEvent()
    data object SearchFavorite : SearchScreenEvent()
    data object SearchAll : SearchScreenEvent()
    data object SearchRandom : SearchScreenEvent()
    data object SortMore : SearchScreenEvent()
    data object SavePreset : SearchScreenEvent()
    data object FiltersMore : SearchScreenEvent()
}
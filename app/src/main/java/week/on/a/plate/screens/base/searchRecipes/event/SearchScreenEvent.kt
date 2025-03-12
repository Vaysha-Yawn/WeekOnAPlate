package week.on.a.plate.screens.base.searchRecipes.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.base.searchRecipes.state.ResultSortType
import week.on.a.plate.screens.base.searchRecipes.state.ResultSortingDirection

sealed interface SearchScreenEvent : Event {
    object Search : SearchScreenEvent
    class FlipFavorite(val recipe: RecipeView, val inFavorite: Boolean) : SearchScreenEvent
    class VoiceSearch(val context: Context) : SearchScreenEvent
    object Back : SearchScreenEvent
    class AddToMenu(val recipeView: RecipeView, val context: Context) : SearchScreenEvent
    class NavigateToFullRecipe(val recipeView: RecipeView) : SearchScreenEvent
    class SelectTag(val recipeTagView: RecipeTagView) : SearchScreenEvent
    class ChangeSort(val type: ResultSortType, val direction: ResultSortingDirection) :
        SearchScreenEvent

    object ToFilter : SearchScreenEvent
    object CreateRecipe : SearchScreenEvent
    object Clear : SearchScreenEvent
    object SearchFavorite : SearchScreenEvent
    object SearchAll : SearchScreenEvent
    object SearchRandom : SearchScreenEvent
    object SortMore : SearchScreenEvent
    object SavePreset : SearchScreenEvent
    object FiltersMore : SearchScreenEvent
}
package week.on.a.plate.search.event

import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.Event
import week.on.a.plate.core.data.recipe.RecipeView

sealed class SearchScreenEvent : Event(){
    class Search(val text: String = "", val filters:List<RecipeTagView> = listOf(), val ingredients:List<IngredientView> = listOf()) : SearchScreenEvent()
    class FlipFavorite(val id: Long, val inFavorite: Boolean) : SearchScreenEvent()
    data object VoiceSearch : SearchScreenEvent()
    data object Back : SearchScreenEvent()
    class AddToMenu(val recipeView: RecipeView): SearchScreenEvent()
    class NavigateToFullRecipe(val recipeView: RecipeView): SearchScreenEvent()
    data object ToFilter : SearchScreenEvent()
}
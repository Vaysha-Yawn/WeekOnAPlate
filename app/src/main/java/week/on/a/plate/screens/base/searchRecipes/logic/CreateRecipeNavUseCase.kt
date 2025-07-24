package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.additional.createRecipe.navigation.RecipeCreateDestination
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.emptyRecipe
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class CreateRecipeNavUseCase @Inject constructor() {
    operator fun invoke(
        state: SearchUIState, onEvent: (MainEvent) -> Unit
    ) {
        val recipeStart = getRecipeBaseFromSearchState(state)
        onEvent(MainEvent.Navigate(RecipeCreateDestination(null, true, recipeStart)))
    }

    private fun getRecipeBaseFromSearchState(state: SearchUIState): RecipeView {
        val listRecipe = mutableListOf<IngredientInRecipeView>()
        state.selectedIngredients.value.forEach {
            val ingredient = IngredientInRecipeView(0, it, "", 0)
            listRecipe.add(ingredient)
        }
        val recipeStart = emptyRecipe.copy(
            name = state.searchText.value,
            tags = state.selectedTags.value,
            ingredients = listRecipe
        )
        return recipeStart
    }
}



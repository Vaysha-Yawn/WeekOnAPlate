package week.on.a.plate.screens.base.searchRecipes.logic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.base.searchRecipes.state.SearchState
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SearchManager @Inject constructor() {
    suspend fun search(
        state: SearchUIState,
        floAllRecipe: StateFlow<List<RecipeView>>,
    ) = coroutineScope {
        searchAbstract(
            state, floAllRecipe
        ) { recipeView ->
            (if (state.favoriteChecked.value) {
                recipeView.inFavorite
            } else true)
                    && (if (state.allTime.intValue != 0) {
                recipeView.duration.toSecondOfDay() <= state.allTime.intValue * 60
            } else true)
                    && recipeView.name.contains(state.searchText.value.trim(), true)
                    && recipeView.tags.containsAll(state.selectedTags.value)
                    && recipeView.ingredients.map { ingredientInRecipeView -> ingredientInRecipeView.ingredientView }
                .containsAll(state.selectedIngredients.value)
        }
    }

    private suspend fun searchAbstract(
        state: SearchUIState,
        floAllRecipe: StateFlow<List<RecipeView>>,
        filter: (RecipeView) -> Boolean,
    ) = coroutineScope {
        state.searched.value = SearchState.Searching
        launch {
            state.searched.value = SearchState.Done
            floAllRecipe.map { it.filter { t -> filter(t) } }.collect {
                state.resultSearch.value = it.sorted(state)
            }
        }

    }

    suspend fun searchAll(
        state: SearchUIState,
        floAllRecipe: StateFlow<List<RecipeView>>,
    ) = coroutineScope {
        searchAbstract(
            state, floAllRecipe,
        ) { true }
    }

    suspend fun searchRandom(
        state: SearchUIState,
        floAllRecipe: StateFlow<List<RecipeView>>,
    ) = coroutineScope {
        state.searched.value = SearchState.Searching
        launch {
            state.searched.value = SearchState.Done
            floAllRecipe.map { recipeViewList ->
                if (recipeViewList.isNotEmpty()) {
                    val mutableRecipeViewList = recipeViewList.toMutableList()
                    val listRandom = mutableListOf<RecipeView>()
                    while (listRandom.size < 20 && mutableRecipeViewList.isNotEmpty()) {
                        val random = mutableRecipeViewList.random()
                        mutableRecipeViewList.remove(random)
                        listRandom.add(random)
                    }
                    listRandom
                } else {
                    recipeViewList
                }
            }.collect {
                state.resultSearch.value = it.sorted(state)
            }
        }
    }

    suspend fun searchFavorite(
        state: SearchUIState,
        floAllRecipe: StateFlow<List<RecipeView>>,
    ) {
        searchAbstract(
            state, floAllRecipe
        ) { recipeView ->
            recipeView.inFavorite
        }
    }

}
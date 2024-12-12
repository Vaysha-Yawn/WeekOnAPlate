package week.on.a.plate.screens.searchRecipes.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

class SearchUIState {
    var allTagsCategories: State<List<TagCategoryView>> = mutableStateOf(listOf())
    val resultSearch = mutableStateOf<List<RecipeView>>(listOf())
    val searchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val searched = mutableStateOf(SearchState.none)
    val modeResultViewIsList = mutableStateOf(false)
    val resultSortType = mutableStateOf(Pair(ResultSortType.date, ResultSortingDirection.down))
    val favoriteChecked = mutableStateOf(false)
    val allTime = mutableIntStateOf(0)
    val prepTime = mutableIntStateOf(0)
}

enum class ResultSortType{ date, alphabet, randow }
enum class ResultSortingDirection{ up, down }
enum class SearchState{ none, searching, done }


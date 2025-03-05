package week.on.a.plate.screens.base.searchRecipes.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

//todo задуматься о том, чтобы вместо множества полей как state сделать state, как дата и делать copy () и неизменяемое состояние будет
class SearchUIState {
    var allTagsCategories: State<List<TagCategoryView>> = mutableStateOf(listOf())
    val resultSearch = mutableStateOf<List<RecipeView>>(listOf())
    val searchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val searched = mutableStateOf(SearchState.None)
    val modeResultViewIsList = mutableStateOf(false)
    val resultSortType = mutableStateOf(Pair(ResultSortType.Date, ResultSortingDirection.Down))
    val favoriteChecked = mutableStateOf(false)
    val allTime = mutableIntStateOf(0)
    val prepTime = mutableIntStateOf(0)
    var selId: Long? = null
}

enum class ResultSortType { Date, Alphabet, Random }
enum class ResultSortingDirection { Up, Down }
enum class SearchState { None, Searching, Done }


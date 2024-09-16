package week.on.a.plate.search.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.RecipeView
import week.on.a.plate.search.data.SearchCategories.Companion.SearchCategoriesEmpty

class SearchUIState {
    val allCategories: MutableState<SearchCategories> = mutableStateOf(SearchCategoriesEmpty)
    val resultSearch:MutableState<List<RecipeView>> = mutableStateOf(listOf())
    val searchText = mutableStateOf("")
    val filtersSearchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val showFilter = mutableStateOf(false)
}


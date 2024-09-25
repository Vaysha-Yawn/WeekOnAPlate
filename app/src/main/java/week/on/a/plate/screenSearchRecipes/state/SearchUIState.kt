package week.on.a.plate.screenSearchRecipes.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

class SearchUIState {
    var allTagsCategories: MutableState<List<TagCategoryView>> = mutableStateOf(listOf())
    val resultSearch:MutableState<List<RecipeView>> = mutableStateOf(listOf())
    val searchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val isSearchInWeb = mutableStateOf(false)
}


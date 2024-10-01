package week.on.a.plate.screenSearchCategories.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.TagCategoryView
import week.on.a.plate.data.repository.tables.filters.recipeTagCategory.RecipeTagCategoryRepository

class CategoriesSearchUIState(){
    var allTags: State<List<TagCategoryView>> =  mutableStateOf(listOf())
    var allIngredients: State<List<IngredientCategoryView>> =  mutableStateOf(listOf())
    val resultSearch: MutableState<List<String>> =  mutableStateOf(listOf())
    val searchText: MutableState<String> = mutableStateOf("")
}



package week.on.a.plate.screenFilters.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

class FilterUIState {
    var allTagsCategories: State<List<TagCategoryView>> = mutableStateOf(listOf())
    var allIngredientsCategories: State<List<IngredientCategoryView>> = mutableStateOf(listOf())
    val filtersSearchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val activeFilterTabIndex = mutableIntStateOf(0)
    val resultSearchFilterTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val resultSearchFilterIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val filterMode = mutableStateOf<FilterMode>(FilterMode.All)
}


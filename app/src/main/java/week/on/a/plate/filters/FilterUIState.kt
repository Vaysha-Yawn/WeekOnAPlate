package week.on.a.plate.filters

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.IngredientCategoryView
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.data.recipe.TagCategoryView

class FilterUIState {
    var allTagsCategories: MutableState<List<TagCategoryView>> = mutableStateOf(listOf())
    var allIngredientsCategories: MutableState <List<IngredientCategoryView> > = mutableStateOf(listOf())
    val filtersSearchText = mutableStateOf("")
    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val activeFilterTabIndex = mutableIntStateOf(0)
    val resultSearchFilterTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val resultSearchFilterIngredients = mutableStateOf<List<IngredientView>>(listOf())
}


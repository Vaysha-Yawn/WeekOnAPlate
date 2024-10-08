package week.on.a.plate.screenFilters.state

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.TagCategoryView

class FilterUIState() {
    val searchText = mutableStateOf("")
    val activeFilterTabIndex = mutableIntStateOf(0)

    val filterMode = mutableStateOf<FilterMode>(FilterMode.Multiple)
    val filterEnum = mutableStateOf( FilterEnum.Ingredient)

    var allTagsCategories: State<List<TagCategoryView>> = mutableStateOf(listOf())
    var allIngredientsCategories: State<List<IngredientCategoryView>> = mutableStateOf(listOf())

    val resultSearchTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val resultSearchIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val resultSearchTagsCategories = mutableStateOf<List<TagCategoryView>>(listOf())
    val resultSearchIngredientsCategories = mutableStateOf<List<IngredientCategoryView>>(listOf())

    val selectedTags = mutableStateOf<List<RecipeTagView>>(listOf())
    val selectedIngredients = mutableStateOf<List<IngredientView>>(listOf())
    val selectedTagsCategories = mutableStateOf<List<TagCategoryView>>(listOf())
    val selectedIngredientsCategories  = mutableStateOf<List<IngredientCategoryView>>(listOf())

    fun getCopy():FilterUIState{
        val old = this
        val new =  FilterUIState()
        with(new){
            activeFilterTabIndex.intValue = old.activeFilterTabIndex.intValue
            filterMode.value = old.filterMode.value
            filterEnum.value = old.filterEnum.value
            selectedTags.value = old.selectedTags.value
            selectedIngredients.value =old.selectedIngredients.value
        }
        return new
    }

    fun restoreState(savedState:FilterUIState){
        activeFilterTabIndex.intValue = savedState.activeFilterTabIndex.intValue
        filterMode.value = savedState.filterMode.value
        filterEnum.value = savedState.filterEnum.value
        selectedTags.value = savedState.selectedTags.value
        selectedIngredients.value = savedState.selectedIngredients.value
    }
}


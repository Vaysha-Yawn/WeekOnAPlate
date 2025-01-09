package week.on.a.plate.screens.filters.dialogs.selectedFilters.state

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

class SelectedFiltersUIState(
    selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>,
) {
    val selectedTags = mutableStateOf<List<RecipeTagView>>(selectedTags)
    val selectedIngredients = mutableStateOf<List<IngredientView>>(selectedIngredients)
    val activeFilterTabIndex = mutableIntStateOf(0)
}



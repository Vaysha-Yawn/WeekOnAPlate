package week.on.a.plate.screens.additional.filters.dialogs.filterVoiceApply.state

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView

class FilterVoiceApplyUIState(
    selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>,
) {
    val selectedTags = mutableStateOf<List<RecipeTagView>>(selectedTags)
    val selectedIngredients = mutableStateOf<List<IngredientView>>(selectedIngredients)
}



package week.on.a.plate.core.dialogs.filter.filterVoiceApply.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView

class FilterVoiceApplyUIState(
    selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>,
) {
    val selectedTags = mutableStateOf<List<RecipeTagView>>(selectedTags)
    val selectedIngredients = mutableStateOf<List<IngredientView>>(selectedIngredients)
    val show: MutableState<Boolean> = mutableStateOf(true)
}



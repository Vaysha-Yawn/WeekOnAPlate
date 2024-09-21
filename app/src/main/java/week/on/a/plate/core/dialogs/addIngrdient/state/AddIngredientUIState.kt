package week.on.a.plate.core.dialogs.addIngrdient.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.IngredientCategoryView

class AddIngredientUIState(
    name: String, measure: String, category: IngredientCategoryView?,
    photoUri: String,
) {
    val name: MutableState<String> = mutableStateOf(name)
    val measure: MutableState<String> = mutableStateOf(measure)
    val category: MutableState<IngredientCategoryView?> = mutableStateOf(category)
    val photoUri: MutableState<String> = mutableStateOf(photoUri)
    val show: MutableState<Boolean> = mutableStateOf(true)
}


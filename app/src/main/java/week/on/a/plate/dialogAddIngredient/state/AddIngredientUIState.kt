package week.on.a.plate.dialogAddIngredient.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView

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


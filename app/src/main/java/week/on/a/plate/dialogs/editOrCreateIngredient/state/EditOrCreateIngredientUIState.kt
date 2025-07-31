package week.on.a.plate.dialogs.editOrCreateIngredient.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.IngredientCategoryView

class EditOrCreateIngredientUIState(
    name: String, isLiquid: Boolean, category: IngredientCategoryView?,
    photoUri: String, isForCreate: Boolean,
) {
    val name: MutableState<String> = mutableStateOf(name)
    val isLiquid: MutableState<Boolean> = mutableStateOf(isLiquid)
    val category: MutableState<IngredientCategoryView?> = mutableStateOf(category)
    val photoUri: MutableState<String> = mutableStateOf(photoUri)
    val isForCreate: MutableState<Boolean> = mutableStateOf(isForCreate)
}


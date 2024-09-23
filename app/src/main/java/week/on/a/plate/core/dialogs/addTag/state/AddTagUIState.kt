package week.on.a.plate.core.dialogs.addTag.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.TagCategoryView

class AddTagUIState (
){
    val category: MutableState<TagCategoryView?> = mutableStateOf(null)
    val categoryName: MutableState<String?> = mutableStateOf("")
    val text: MutableState<String> = mutableStateOf("")
    val show: MutableState<Boolean> = mutableStateOf(true)
}



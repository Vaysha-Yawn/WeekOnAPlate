package week.on.a.plate.dialogs.editOrCreateTag.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.recipe.TagCategoryView

class AddTagUIState (
){
    val category: MutableState<TagCategoryView?> = mutableStateOf(null)
    val text: MutableState<String> = mutableStateOf("")
}



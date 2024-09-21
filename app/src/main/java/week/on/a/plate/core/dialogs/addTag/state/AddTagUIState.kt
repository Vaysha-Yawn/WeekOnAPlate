package week.on.a.plate.core.dialogs.addTag.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.recipe.TagCategoryView

class AddTagUIState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetState: SheetState,
    startName:String?,
    startCategory:TagCategoryView?
){
    val category: MutableState<TagCategoryView?> = mutableStateOf(startCategory)
    val text: MutableState<String> = mutableStateOf(startName?:"")
    val show: MutableState<Boolean> = mutableStateOf(true)
}



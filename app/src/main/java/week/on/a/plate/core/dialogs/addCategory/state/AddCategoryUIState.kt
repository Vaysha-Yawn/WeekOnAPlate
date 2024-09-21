package week.on.a.plate.core.dialogs.addCategory.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class AddCategoryUIState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetState: SheetState,
    private val startName:String?
){
    val text: MutableState<String> = mutableStateOf(startName?:"")
    val show: MutableState<Boolean> = mutableStateOf(true)
}



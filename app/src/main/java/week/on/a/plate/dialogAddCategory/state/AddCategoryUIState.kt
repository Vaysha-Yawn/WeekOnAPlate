package week.on.a.plate.dialogAddCategory.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class AddCategoryUIState {
    val text: MutableState<String> = mutableStateOf("")
    val show: MutableState<Boolean> = mutableStateOf(true)
}



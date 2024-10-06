package week.on.a.plate.dialogEditOneString.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class EditOneStringUIState(startText:String = "", startTitle:String = "", startPlaceholder :String = "Введите текст здесь...") {
    val text: MutableState<String> = mutableStateOf(startText)
    val title: MutableState<String> = mutableStateOf(startTitle)
    val placeholder: MutableState<String> = mutableStateOf(startPlaceholder)
}



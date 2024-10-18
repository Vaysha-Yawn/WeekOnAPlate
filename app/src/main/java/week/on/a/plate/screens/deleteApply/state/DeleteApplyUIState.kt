package week.on.a.plate.screens.deleteApply.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class DeleteApplyUIState() {
    val title:MutableState<String> = mutableStateOf("Уверены, что хотите удалить?")
    val message:MutableState<String> = mutableStateOf("")
}
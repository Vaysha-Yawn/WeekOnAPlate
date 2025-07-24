package week.on.a.plate.screens.additional.deleteApply.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class DeleteApplyUIState() {
    val title: MutableState<String?> = mutableStateOf(null)
    val message:MutableState<String> = mutableStateOf("")
}
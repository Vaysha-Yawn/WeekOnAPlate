package week.on.a.plate.core.dialogs.dialogAbstract.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class UIState(
){
    val show: MutableState<Boolean> = mutableStateOf(true)
}



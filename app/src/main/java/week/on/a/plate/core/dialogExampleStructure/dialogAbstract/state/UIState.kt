package week.on.a.plate.core.dialogExampleStructure.dialogAbstract.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class UIState(
){
    val show: MutableState<Boolean> = mutableStateOf(true)
}



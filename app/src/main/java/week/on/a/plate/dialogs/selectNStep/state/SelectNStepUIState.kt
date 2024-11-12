package week.on.a.plate.dialogs.selectNStep.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class SelectNStepUIState(
    val stepCount:Int
){
    val show: MutableState<Boolean> = mutableStateOf(true)
}



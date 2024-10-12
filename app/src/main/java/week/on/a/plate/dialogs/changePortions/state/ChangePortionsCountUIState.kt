package week.on.a.plate.dialogs.changePortions.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class ChangePortionsCountUIState{
    val portionsCount = mutableIntStateOf(0)
    val show: MutableState<Boolean> = mutableStateOf(true)
}



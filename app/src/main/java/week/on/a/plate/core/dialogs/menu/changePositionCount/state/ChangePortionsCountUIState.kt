package week.on.a.plate.core.dialogs.menu.changePositionCount.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class ChangePortionsCountUIState{
    val portionsCount = mutableIntStateOf(0)
    val show: MutableState<Boolean> = mutableStateOf(true)
}



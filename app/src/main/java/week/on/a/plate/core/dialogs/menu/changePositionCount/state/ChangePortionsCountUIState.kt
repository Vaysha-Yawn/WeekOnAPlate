package week.on.a.plate.core.dialogs.menu.changePositionCount.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

class ChangePortionsCountUIState @OptIn(ExperimentalMaterial3Api::class) constructor(
    val sheetState: SheetState,
    private val startPortionsCount:Int
){
    val portionsCount = mutableIntStateOf(startPortionsCount)
    val show: MutableState<Boolean> = mutableStateOf(true)
}



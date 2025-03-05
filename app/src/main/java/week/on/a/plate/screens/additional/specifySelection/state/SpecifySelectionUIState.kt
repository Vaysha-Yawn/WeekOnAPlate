package week.on.a.plate.screens.additional.specifySelection.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

class SpecifySelectionUIState() {
    var nonPosedText = ""
    val checkWeek = mutableStateOf<Boolean>(false)
    val checkDayCategory = mutableStateOf<String?>(null)
    val date = mutableStateOf<LocalDate>(LocalDate.now())
    val portionsCount = mutableIntStateOf(2)
    val allSelectionsIdDay = mutableStateOf<List<String>>(listOf())
    val dayViewPreview:MutableState<List<SelectionView>> = mutableStateOf(listOf())
}
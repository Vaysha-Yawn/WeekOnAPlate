package week.on.a.plate.screens.cookPlanner.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

class CookPlannerUIState() {
    val checkWeek = mutableStateOf<Boolean>(false)
    val checkDayCategory = mutableStateOf<String?>(null)
    val date = mutableStateOf<LocalDate?>(null)
    val portionsCount = mutableIntStateOf(2)
    val allSelectionsIdDay = mutableStateOf<List<String>>(listOf())
    val dayViewPreview:MutableState<List<SelectionView>> = mutableStateOf(listOf())
    val isDateChooseActive:MutableState<Boolean> = mutableStateOf(false)
}
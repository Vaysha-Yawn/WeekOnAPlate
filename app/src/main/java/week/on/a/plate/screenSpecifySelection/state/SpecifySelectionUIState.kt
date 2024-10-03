package week.on.a.plate.screenSpecifySelection.state

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.SelectionView
import java.time.LocalDate

class SpecifySelectionUIState() {
    val checkWeek = mutableStateOf<Boolean>(false)
    val checkDayCategory = mutableStateOf<String?>(null)
    val date = mutableStateOf<LocalDate?>(null)
    val portionsCount = mutableIntStateOf(2)
    val allSelectionsIdDay = mutableStateOf<List<String>>(listOf())
}
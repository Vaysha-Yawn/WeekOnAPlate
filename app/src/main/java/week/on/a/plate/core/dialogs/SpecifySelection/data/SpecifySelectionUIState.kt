package week.on.a.plate.core.dialogs.SpecifySelection.data

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.week.CategoriesSelection
import java.time.LocalDate

class SpecifySelectionUIState() {
    val checkWeek = mutableStateOf<Boolean>(false)
    val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
    val date = mutableStateOf<LocalDate?>(null)
}
package week.on.a.plate.screenInventory.state

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.CategoriesSelection
import java.time.LocalDate

class InventoryUIState() {
    val checkWeek = mutableStateOf<Boolean>(false)
    val checkDayCategory = mutableStateOf<CategoriesSelection?>(null)
    val date = mutableStateOf<LocalDate?>(null)
    val portionsCount = mutableIntStateOf(2)
}
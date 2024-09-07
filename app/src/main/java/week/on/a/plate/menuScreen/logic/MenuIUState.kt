package week.on.a.plate.menuScreen.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import week.on.a.plate.core.data.example.EmptyWeek
import week.on.a.plate.core.data.week.WeekView

data class MenuIUState(
    var week: WeekView,
    val chosenRecipes: MutableMap<Long, MutableState<Boolean>>,
    val itsDayMenu: MutableState<Boolean>,
    val editing: MutableState<Boolean>,
    val activeDayInd: MutableState<Int>,
) {
    companion object {
        val MenuIUStateExample = MenuIUState(
            EmptyWeek,
            mutableMapOf<Long, MutableState<Boolean>>(),
            mutableStateOf(true),
            mutableStateOf(false),
            mutableIntStateOf(0)
        )
    }
}
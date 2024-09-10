package week.on.a.plate.menuScreen.logic.eventData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf

data class MenuIUState(
    var chosenRecipes: MutableMap<Long, MutableState<Boolean>>,
    val itsDayMenu: MutableState<Boolean>,
    val editing: MutableState<Boolean>,
    val activeDayInd: MutableState<Int>,
    val isAllSelected: MutableState<Boolean>,
    val dialogState: MutableState<DialogMenuData?>
) {
    companion object {
        val MenuIUStateExample = MenuIUState(
            mutableMapOf<Long, MutableState<Boolean>>(),
            mutableStateOf(true),
            mutableStateOf(false),
            mutableIntStateOf(0),
            mutableStateOf(false),
            mutableStateOf(null),
        )
    }
}
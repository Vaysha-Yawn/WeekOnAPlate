package week.on.a.plate.menuScreen.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.week.Position

data class MenuIUState(
    var chosenRecipes: MutableMap<Position.PositionRecipeView, MutableState<Boolean>>,
    val itsDayMenu: MutableState<Boolean>,
    val editing: MutableState<Boolean>,
    val activeDayInd: MutableState<Int>,
    val isAllSelected: MutableState<Boolean>,
    val titleTopBar:MutableState<String>
) {
    companion object {
        val MenuIUStateExample = MenuIUState(
            mutableMapOf<Position.PositionRecipeView, MutableState<Boolean>>(),
            mutableStateOf(true),
            mutableStateOf(false),
            mutableIntStateOf(0),
            mutableStateOf(false),
            mutableStateOf("Август 26-1"),
        )
    }
}
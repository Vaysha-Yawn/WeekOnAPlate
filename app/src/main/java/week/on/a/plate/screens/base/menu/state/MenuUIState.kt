package week.on.a.plate.screens.base.menu.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate

data class MenuUIState(
    var chosenRecipes: MutableMap<Position.PositionRecipeView, MutableState<Boolean>>,
    val isAllSelected: MutableState<Boolean>,
    val week: MutableState<WeekView>,
    val wrapperDatePickerUIState: WrapperDatePickerUIState
) {
    val forWeekFullName = mutableStateOf<String>("")
    val nonPosedFullName = mutableStateOf<String>("")

    companion object {
        val MenuUIStateExample = MenuUIState(
            mutableMapOf(),
            mutableStateOf(false),
            mutableStateOf(WeekDataExample),
            WrapperDatePickerUIState(
                mutableStateOf(LocalDate.now()),
                mutableStateOf(true),
                mutableStateOf("Август 26-1"),
                mutableStateOf(false),)
        )
    }
}


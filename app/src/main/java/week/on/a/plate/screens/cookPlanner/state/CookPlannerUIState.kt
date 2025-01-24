package week.on.a.plate.screens.cookPlanner.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.core.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate

class CookPlannerUIState() {
    val week: MutableState<Map<LocalDate, List<CookPlannerGroupView>>> = mutableStateOf(mapOf())
    val wrapperDatePickerUIState: WrapperDatePickerUIState = WrapperDatePickerUIState(
        activeDay = mutableStateOf(LocalDate.now()),
        itsDayMenu = mutableStateOf(true),
        titleTopBar = mutableStateOf(""),
        isGroupSelectedModeActive = mutableStateOf(false)
    )
}
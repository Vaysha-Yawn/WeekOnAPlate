package week.on.a.plate.screens.base.cookPlanner.state

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate

data class CookPlannerUIState(
    val week: Map<LocalDate, List<CookPlannerGroupView>> = mapOf(),
    val weekResult: List<Pair<LocalDate, Boolean>> = listOf(),
    val wrapperDatePickerUIState: WrapperDatePickerUIState = WrapperDatePickerUIState(
        activeDay = mutableStateOf(LocalDate.now()),
        itsDayMenu = mutableStateOf(true),
        titleTopBar = mutableStateOf(""),
        isGroupSelectedModeActive = mutableStateOf(false)
    )
)
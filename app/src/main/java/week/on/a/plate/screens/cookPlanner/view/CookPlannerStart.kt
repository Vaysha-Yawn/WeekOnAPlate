package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.wrapperDatePicker.view.WrapperDatePicker
import week.on.a.plate.screens.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.cookPlanner.state.CookPlannerUIState

@Composable
fun CookPlannerStart(vm: CookPlannerViewModel) {
    CookPlannerContent(vm.state) { vm.onEvent(it) }
}

@Composable
fun CookPlannerContent(state: CookPlannerUIState, onEvent: (Event) -> Unit) {
    val weekResult = state.week.value.map {
        val date = it.key
        val isPlanned = it.value.isNotEmpty()
        Pair(date, isPlanned)
    }
    WrapperDatePicker(
        state.wrapperDatePickerUIState,
        state.wrapperDatePickerUIState.isGroupSelectedModeActive,
        weekResult,
        onEvent,
        { DayViewCookPlan(
                state.week.value,
                state.wrapperDatePickerUIState.activeDay.value,
                onEvent
            )
        },
        { WeekViewCookPlan(state, onEvent) })
}


@Preview(showBackground = true)
@Composable
fun PreviewCookPlanner() {
    WeekOnAPlateTheme {
        CookPlannerContent(CookPlannerUIState()) {}
    }
}
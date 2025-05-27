package week.on.a.plate.screens.base.cookPlanner.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.base.cookPlanner.logic.CookPlannerViewModel
import week.on.a.plate.screens.base.cookPlanner.state.CookPlannerUIState
import week.on.a.plate.screens.base.wrapperDatePicker.view.WrapperDatePicker

@Composable
fun CookPlannerStart(vm: CookPlannerViewModel = viewModel()) {
    CookPlannerContent(vm.state.value) { vm.onEvent(it) }
    MainEventResolve(vm.mainEvent, vm.dialogOpenParams)
}

@Composable
private fun CookPlannerContent(state: CookPlannerUIState, onEvent: (Event) -> Unit) {
    WrapperDatePicker(
        state.wrapperDatePickerUIState,
        state.wrapperDatePickerUIState.isGroupSelectedModeActive,
        state.weekResult,
        onEvent,
        { DayViewCookPlan(
            state.week,
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
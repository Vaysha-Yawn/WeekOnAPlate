package week.on.a.plate.screens.menu.view.main

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.ads.NativeAdRow
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.menu.state.MenuUIState
import week.on.a.plate.screens.menu.view.day.DayView
import week.on.a.plate.screens.menu.view.week.WeekMenu
import week.on.a.plate.screens.wrapperDatePicker.view.WrapperDatePicker

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(
    vm: week.on.a.plate.screens.menu.logic.MenuViewModel
) {
    MenuScreenSuccess(vm.menuUIState, vm.menuUIState.week.value) { event: Event ->
        vm.onEvent(event)
    }
}

@Composable
fun MenuScreenSuccess(
    uiState: MenuUIState,
    week: WeekView,
    onEvent: (event: Event) -> Unit
) {
    val listDays = week.days.map {
        val date = it.date
        val isPlanned = it.selections.map { it.positions.isNotEmpty() }.contains(true)
        Pair(date, isPlanned)
    }
    WrapperDatePicker(
        uiState.wrapperDatePickerUIState,
        uiState.wrapperDatePickerUIState.isGroupSelectedModeActive,
        listDays,
        onEvent,
        {
            DayView(
                week.days[uiState.wrapperDatePickerUIState.activeDayInd.value],
                uiState,
                onEvent
            )
        },
        {
            WeekMenu(uiState, onEvent, week)
        })
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    WeekOnAPlateTheme {
        MenuScreenSuccess(MenuUIState.MenuUIStateExample, WeekDataExample) {}
    }
}
package week.on.a.plate.screens.base.menu.presenter.view.main

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.base.menu.presenter.logic.MenuViewModel
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.menu.presenter.view.day.DayView
import week.on.a.plate.screens.base.menu.presenter.view.week.WeekMenu
import week.on.a.plate.screens.base.wrapperDatePicker.view.WrapperDatePicker

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(mainViewModel: MainViewModel, vm: MenuViewModel) {
    MenuScreenSuccess(vm.menuUIState.value, vm.menuUIState.value.week) { event: Event ->
        vm.onEvent(event)
    }

    MainEventResolve(vm.mainEvent, vm.dialogOpenParams, mainViewModel)
}

@Composable
private fun MenuScreenSuccess(
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
            val activeDayView =
                week.days.find { it.date == uiState.wrapperDatePickerUIState.activeDay.value }
                    ?: return@WrapperDatePicker
            DayView(
                activeDayView,
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
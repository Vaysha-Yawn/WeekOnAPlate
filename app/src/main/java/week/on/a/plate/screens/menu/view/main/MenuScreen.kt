package week.on.a.plate.screens.menu.view.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.menu.state.MenuUIState
import week.on.a.plate.screens.menu.state.WeekState
import week.on.a.plate.screens.menu.view.day.DayView
import week.on.a.plate.screens.menu.view.week.WeekMenu
import week.on.a.plate.screens.wrapperDatePicker.view.WrapperDatePicker

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(
    vm: week.on.a.plate.screens.menu.logic.MenuViewModel
) {
    val uiState = vm.weekState.collectAsStateWithLifecycle().value
    when (uiState) {
        WeekState.EmptyWeek -> {}
        is WeekState.Error -> {}
        WeekState.Loading -> {
            Log.e("", "Load")
        }
        is WeekState.Success -> {
            Log.e("", "Success")
            if (uiState.week.days.isNotEmpty()) {
                MenuScreenSuccess(vm.menuUIState, uiState.week) { event: Event ->
                    vm.onEvent(event)
                }
            } else {
                vm.weekState.value = WeekState.EmptyWeek
            }
        }
    }

}

@Composable
fun MenuScreenSuccess(
    uiState: MenuUIState,
    week: WeekView,
    onEvent: (event: Event) -> Unit
) {
    val listDays =  week.days.map {
        val date = it.date
        val isPlanned = it.selections.map { it.positions.isNotEmpty() }.contains(true)
        Pair(date, isPlanned)
    }
    WrapperDatePicker(uiState.wrapperDatePickerUIState, uiState.wrapperDatePickerUIState.isGroupSelectedModeActive, listDays, onEvent, {
        DayView(week.days[uiState.wrapperDatePickerUIState.activeDayInd.value], uiState, onEvent)
    }, {
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
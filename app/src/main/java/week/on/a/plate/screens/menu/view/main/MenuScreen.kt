package week.on.a.plate.screens.menu.view.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.menu.state.MenuUIState
import week.on.a.plate.screens.menu.state.WeekState
import week.on.a.plate.screens.wrapperDatePicker.state.WrapperDatePickerUIState
import week.on.a.plate.screens.wrapperDatePicker.view.calendar.BlockCalendar
import week.on.a.plate.screens.menu.view.day.DayView
import week.on.a.plate.screens.wrapperDatePicker.view.topBar.TopBar
import week.on.a.plate.screens.menu.view.week.WeekMenu

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
    WrapperDatePicker(uiState.wrapperDatePickerUIState, uiState.wrapperDatePickerUIState.isGroupSelectedModeActive, week, onEvent, {
        DayView(week.days[uiState.wrapperDatePickerUIState.activeDayInd.value], uiState, onEvent)
    }, {
        WeekMenu(uiState, onEvent, week)
    })
}

@Composable
fun WrapperDatePicker(
    uiState: WrapperDatePickerUIState, editing: MutableState<Boolean>,
    week: WeekView,
    onEvent: (event: Event) -> Unit, dayContent: @Composable ()->Unit, weekContent: @Composable ()->Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp)
    ) {
        val weekTitle = uiState.titleTopBar.value
        val curDay = week.days[uiState.activeDayInd.value].date.dateToString()
        TopBar(weekTitle, curDay, editing, uiState,
            actionDeleteSelected = {onEvent(MenuEvent.DeleteSelected)},
            actionSelectedToShopList = {onEvent(MenuEvent.SelectedToShopList)}, onEvent)
        if (uiState.itsDayMenu.value) {
            Spacer(modifier = Modifier.height(12.dp))
            val daysForCalendar = week.days.map {
                val date = it.date
                val isPlanned = (it.selections.any { sel -> sel.positions.isNotEmpty() })
                Pair(date, isPlanned)
            }
            BlockCalendar(daysForCalendar, uiState, changeDay = { ind ->
                uiState.activeDayInd.value = ind
            }, chooseLastWeek = {
                onEvent(WrapperDatePickerEvent.ChangeWeek(week.days[uiState.activeDayInd.value].date.minusWeeks(1)))
            }, chooseNextWeek = {
                onEvent(WrapperDatePickerEvent.ChangeWeek(week.days[uiState.activeDayInd.value].date.plusWeeks(1)))
            })
            Spacer(Modifier.height(24.dp))
        }

        if (uiState.itsDayMenu.value) {
            dayContent()
        } else {
            weekContent()
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    WeekOnAPlateTheme {
        MenuScreenSuccess(MenuUIState.MenuUIStateExample, WeekDataExample) {}
    }
}
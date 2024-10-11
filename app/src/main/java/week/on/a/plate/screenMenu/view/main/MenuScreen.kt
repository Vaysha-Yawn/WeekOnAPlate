package week.on.a.plate.screenMenu.view.main

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.data.dataView.example.WeekDataExample
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.logic.MenuViewModel
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.state.WeekState
import week.on.a.plate.screenMenu.view.calendar.BlockCalendar
import week.on.a.plate.screenMenu.view.day.DayView
import week.on.a.plate.screenMenu.view.topBar.TopBar
import week.on.a.plate.screenMenu.view.week.WeekMenu
import java.time.LocalDate

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(
    vm: MenuViewModel
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
    uiState: MenuIUState,
    week: WeekView,
    onEvent: (event: Event) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp)
    ) {
        val curDay = week.days[uiState.activeDayInd.value].date.dateToString()
        val weekTitle = uiState.titleTopBar.value
        TopBar(weekTitle, curDay, uiState, onEvent)
        if (uiState.itsDayMenu.value) {
            Spacer(modifier = Modifier.height(12.dp))
            BlockCalendar(week.days, LocalDate.now(), uiState.activeDayInd.value, changeDay = { ind ->
                uiState.activeDayInd.value = ind
            }, chooseLastWeek = {
                onEvent(MenuEvent.ChangeWeek(week.days[uiState.activeDayInd.value].date.minusWeeks(1)))
            }, chooseNextWeek = {
                onEvent(MenuEvent.ChangeWeek(week.days[uiState.activeDayInd.value].date.plusWeeks(1)))
            })
            Spacer(Modifier.height(24.dp))
        }

        if (uiState.itsDayMenu.value) {
            DayView(week.days[uiState.activeDayInd.value], uiState, onEvent)
        } else {
            WeekMenu(uiState, onEvent, week)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    WeekOnAPlateTheme {
        MenuScreenSuccess(MenuIUState.MenuIUStateExample, WeekDataExample) {}
    }
}
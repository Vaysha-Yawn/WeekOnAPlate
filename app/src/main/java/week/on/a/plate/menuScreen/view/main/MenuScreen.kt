package week.on.a.plate.menuScreen.view.main

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.MenuIUState
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.logic.eventData.WeekState
import week.on.a.plate.menuScreen.view.day.calendar.BlockCalendar
import week.on.a.plate.menuScreen.view.day.DayView
import week.on.a.plate.menuScreen.view.day.NoDay
import week.on.a.plate.menuScreen.view.dialogs.DialogsContainer
import week.on.a.plate.menuScreen.view.week.WeekMenu
import week.on.a.plate.menuScreen.view.uiTools.TopBar
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.time.LocalDate

@Composable
fun MenuScreen(viewModel: MenuViewModel = hiltViewModel()) {
    val uiState = viewModel.weekState.collectAsStateWithLifecycle().value
    when (uiState) {
        WeekState.EmptyWeek -> {}
        is WeekState.Error -> {}
        WeekState.Loading -> {}
        is WeekState.Success -> {
            MenuScreenSuccess(viewModel.menuUIState, uiState.week) { event: MenuEvent ->
                viewModel.onEvent(event)
            }
        }
    }
}

@Composable
fun MenuScreenSuccess(
    uiState: MenuIUState,
    week: WeekView,
    onEvent: (event: MenuEvent) -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 10.dp)
    ) {
        TopBar(week, "Август 26-1", uiState, onEvent)

        if (uiState.itsDayMenu.value) {
            Spacer(modifier = Modifier.height(24.dp))
            BlockCalendar(week.days, LocalDate.now(), uiState.activeDayInd.value) { ind ->
                uiState.activeDayInd.value = ind
            }
            Spacer(Modifier.height(24.dp))
        }

        if (uiState.itsDayMenu.value) {
            if (week.days[uiState.activeDayInd.value].selections.isEmpty()){
                NoDay(week.days[uiState.activeDayInd.value].date, onEvent)
            }else{
                DayView(week.days[uiState.activeDayInd.value], uiState, onEvent)
            }
        } else {
            WeekMenu(uiState, onEvent, week)
        }
    }
    DialogsContainer(uiState, onEvent)
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    WeekOnAPlateTheme {
        MenuScreenSuccess(MenuIUState.MenuIUStateExample, WeekDataExample) {}
    }
}
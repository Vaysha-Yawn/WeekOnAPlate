package week.on.a.plate.menuScreen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.logic.WeekState
import week.on.a.plate.menuScreen.view.calendar.BlockCalendar
import week.on.a.plate.menuScreen.view.day.DayView
import week.on.a.plate.menuScreen.view.day.WeekView
import week.on.a.plate.menuScreen.view.uiTools.ButtonMenuNav
import week.on.a.plate.menuScreen.view.uiTools.EditingRow
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
            viewModel.menuUIState.week = uiState.week
            MenuScreenSuccess(viewModel.menuUIState) { event: MenuEvent ->
                viewModel.onEvent(event)
            }
        }
    }
}


@Composable
fun MenuScreenSuccess(
    uiState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    Column(Modifier.padding(top = 30.dp)) {
        Row(
            Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 20.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.End
        ) {
            ButtonMenuNav(itsDayMenu = uiState.itsDayMenu.value) {
                onEvent(MenuEvent.SwitchWeekOrDayView)
            }
        }
        if (uiState.itsDayMenu.value) {
            BlockCalendar(uiState.week.days, LocalDate.now(), uiState.activeDayInd.value) { ind ->
                uiState.activeDayInd.value = ind
            }
            Spacer(Modifier.height(20.dp))
        }
        if (uiState.editing.value) {
            EditingRow(actionChooseAll = {
                onEvent(MenuEvent.ChooseAll)
            }, actionDeleteSelected = {
                onEvent(MenuEvent.DeleteSelected)
            }, actionSelectedToShopList = {
                onEvent(MenuEvent.SelectedToShopList)
            })
            Spacer(Modifier.height(10.dp))
        }
        if (uiState.itsDayMenu.value) {
            DayView(uiState.week.days[uiState.activeDayInd.value], uiState, onEvent)
        } else {
            WeekView( uiState, onEvent)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    //val vm = MenuViewModel()
    WeekOnAPlateTheme {
        //MenuScreen(vm)
    }
}
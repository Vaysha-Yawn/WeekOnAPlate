package week.on.a.plate.menuScreen.view.main

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.fullScreenDialogs.navigation.FullScreenDialogRoute
import week.on.a.plate.core.dialogs.data.DialogData
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.logic.MenuViewModel
import week.on.a.plate.menuScreen.data.stateData.WeekState
import week.on.a.plate.menuScreen.view.calendar.BlockCalendar
import week.on.a.plate.menuScreen.view.day.DayView
import week.on.a.plate.menuScreen.view.day.NoDay
import week.on.a.plate.menuScreen.view.week.WeekMenu
import week.on.a.plate.menuScreen.view.topBar.TopBar
import week.on.a.plate.ui.theme.WeekOnAPlateTheme
import java.time.LocalDate

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MenuScreen(
    mainVM: MainViewModel,
    viewModel: MenuViewModel = hiltViewModel(),
) {
    viewModel.mainViewModel = mainVM
    viewModel.updateWeek()

    val uiState = viewModel.weekState.collectAsStateWithLifecycle().value
    when (uiState) {
        WeekState.EmptyWeek -> {}
        is WeekState.Error -> {}
        WeekState.Loading -> {}
        is WeekState.Success -> {
            if (uiState.week != null && uiState.week.days.isNotEmpty()) {
                MenuScreenSuccess(viewModel.menuUIState, uiState.week) { event: Event ->
                    viewModel.onEvent(event)
                }
            } else {
                viewModel.weekState.value = WeekState.EmptyWeek
            }
        }
    }

   /* getOptionArgFromSpecifyDate(mainVM.nav){ event: MainEvent ->
        viewModel.onEvent(event)
    }*/

}

fun getOptionArgFromSpecifyDate(navController: NavHostController, onEvent: (MainEvent) -> Unit) {
    val noResultData =
        navController.currentBackStackEntry?.savedStateHandle?.getStateFlow<Long>("selId", 0)

    if (noResultData?.value!=null && noResultData.value!=0L){
       /* onEvent(MainEvent.OpenDialog(DialogData.AddPosition(noResultData.value) { event: MainEvent ->
            onEvent(event)
        }))*/
        navController.currentBackStackEntry?.savedStateHandle?.remove<Long>("selId")

        navController.popBackStack(FullScreenDialogRoute.SpecifyDateDialog, false)
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
        TopBar(uiState.titleTopBar.value, uiState, onEvent)

        if (uiState.itsDayMenu.value) {
            Spacer(modifier = Modifier.height(24.dp))
            BlockCalendar(week.days, LocalDate.now(), uiState.activeDayInd.value) { ind ->
                uiState.activeDayInd.value = ind
            }
            Spacer(Modifier.height(24.dp))
        }

        if (uiState.itsDayMenu.value) {
            if (week.days[uiState.activeDayInd.value].selections.isEmpty()) {
                NoDay(week.days[uiState.activeDayInd.value].date, onEvent)
            } else {
                DayView(week.days[uiState.activeDayInd.value], uiState, onEvent)
            }
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
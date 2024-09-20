package week.on.a.plate.fullScreenDialogs.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.fullScreenDialogs.navigation.FullScreenDialogRoute
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogData
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogsEvent
import week.on.a.plate.fullScreenDialogs.logic.FullScreenDialogsViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialogMain(
    arguments: FullScreenDialogRoute,
    mainViewModel: MainViewModel,
    viewModel: FullScreenDialogsViewModel = hiltViewModel(),
) {
    viewModel.mainViewModel = mainViewModel

    val state = rememberDatePickerState()
    val onEvent = { eventData: Event ->
        viewModel.onEvent(eventData)
    }
    when (arguments) {
        is FullScreenDialogRoute.AddPositionToMenuDialog -> {
            val data = FullScreenDialogData.AddPositionToMenu(
                state, arguments.position, LocalDate.ofEpochDay( arguments.dateFromEpochDay), arguments.category, onEvent
            )

            AddRecipeDialogContent(data = data, onEvent)
        }

        is FullScreenDialogRoute.DoublePositionToMenuDialog -> {
            val data = FullScreenDialogData.DoublePositionToMenu(
                state, arguments.position, onEvent
            )
            DoublePositionDialogContent(data = data, onEvent)
        }

        is FullScreenDialogRoute.MovePositionToMenuDialog -> {
            val data = FullScreenDialogData.MovePositionToMenu(
                state, arguments.position, onEvent
            )
            MovePositionDialogContent(data = data, onEvent)
        }

        is FullScreenDialogRoute.SpecifyDateDialog -> {
            val data = FullScreenDialogData.SpecifyDate(
                state, onEvent
            )
            SpecifyDatePositionDialogContent(data, onEvent)
        }

        is FullScreenDialogRoute.SpecifyDateAndAddRecipeDialog -> {
            //todo
        }
    }
}
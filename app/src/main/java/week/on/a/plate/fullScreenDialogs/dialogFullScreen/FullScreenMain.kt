package week.on.a.plate.fullScreenDialogs.dialogFullScreen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import week.on.a.plate.core.navigation.destiations.FullScreenDialogRoute
import week.on.a.plate.fullScreenDialogs.FullScreenDialogData
import week.on.a.plate.fullScreenDialogs.FullScreenDialogsEvent
import week.on.a.plate.fullScreenDialogs.FullScreenDialogsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FullScreenDialogMain(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    arguments: FullScreenDialogRoute,
    viewModel: FullScreenDialogsViewModel = hiltViewModel(),
) {
    viewModel.snackbarHostState = snackbarHostState
    viewModel.setNavController(navController)
    val state = rememberDatePickerState()
    val onEvent = { eventData: FullScreenDialogsEvent ->
        viewModel.onEvent(eventData)
    }
    when (arguments) {
        is FullScreenDialogRoute.AddPositionToMenuDialog -> {
            val data = FullScreenDialogData.AddPositionToMenu(
                state, arguments.position, arguments.date, arguments.category, onEvent
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
                state, onEvent, {}
            )
            SpecifyDatePositionDialogContent(data, onEvent)
        }
    }
}
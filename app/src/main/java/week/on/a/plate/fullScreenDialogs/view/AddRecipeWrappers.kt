package week.on.a.plate.fullScreenDialogs.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import week.on.a.plate.R
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogData
import week.on.a.plate.fullScreenDialogs.data.FullScreenDialogsEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecifyDatePositionDialogContent(
    data: FullScreenDialogData.SpecifyDate,
    onEvent: (FullScreenDialogsEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent(
        stringResource(R.string.add_position),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker, onEvent,
        data.done, data.close)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoublePositionDialogContent(
    data: FullScreenDialogData.DoublePositionToMenu,
    onEvent: (FullScreenDialogsEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent(
        stringResource(data.titleRes),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker, onEvent,
        data.done, data.close
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovePositionDialogContent(
    data: FullScreenDialogData.MovePositionToMenu,
    onEvent: (FullScreenDialogsEvent) -> Unit
) {
    AddMoveDoubleRecipeDialogContent(
        stringResource(data.titleRes),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker, onEvent,
        data.done, data.close
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeDialogContent(data: FullScreenDialogData.AddPositionToMenu, onEvent: (FullScreenDialogsEvent) -> Unit) {
    data.setUpStartSettings
    AddMoveDoubleRecipeDialogContent(
        stringResource(data.titleRes),
        data.state, data.checkWeek, data.checkDayCategory, data.showDatePicker, onEvent,
        data.done, data.close
    )
}
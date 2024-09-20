package week.on.a.plate.core.dialogs.view.menu

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionRecipeExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.dialogs.editRecipePosition.view.ButtonRow
import week.on.a.plate.menuScreen.data.eventData.ActionWeekMenuDB
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.fullScreenDialogs.navigation.FullScreenDialogRoute
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOtherPositionDialogContent(position:Position, onEvent: (MainEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        val state = rememberDatePickerState()
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ButtonRow(
            R.drawable.edit,
            stringResource(R.string.edit),
        ){
            onEvent(MainEvent.CloseDialog)
            when(position){
                is Position.PositionDraftView -> {
                    //nav
                }
                is Position.PositionIngredientView -> {
                   // onEvent(MainEvent.OpenDialog(DialogData.EditIngredient(position, stateBottom, onEvent)))
                }
                is Position.PositionNoteView -> {
                   // onEvent(MainEvent.OpenDialog(DialogData.EditNote( position, stateBottom, onEvent)))
                }
                is Position.PositionRecipeView -> {
                    //nope
                }
            }
        }

        ButtonRow(
            R.drawable.back_key,
            stringResource(R.string.move),
        ){
            onEvent(MainEvent.CloseDialog)
            onEvent(MainEvent.Navigate(FullScreenDialogRoute.MovePositionToMenuDialog(position)))
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ){
            onEvent(MainEvent.CloseDialog)
            onEvent(MainEvent.Navigate(FullScreenDialogRoute.DoublePositionToMenuDialog(position)))
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ){
            onEvent(MainEvent.CloseDialog)
            onEvent(MainEvent.ActionDBMenu(ActionWeekMenuDB.Delete(position)))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOtherPanel() {
    WeekOnAPlateTheme {
        EditOtherPositionDialogContent(positionRecipeExample, {})
    }
}
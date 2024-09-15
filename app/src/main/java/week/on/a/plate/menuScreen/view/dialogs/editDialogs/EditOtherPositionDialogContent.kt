package week.on.a.plate.menuScreen.view.dialogs.editDialogs

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
import week.on.a.plate.menuScreen.logic.eventData.ActionMenuDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOtherPositionDialogContent(position:Position, onEvent: (MenuEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        val state = rememberDatePickerState()
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ButtonRow(
            R.drawable.edit,
            stringResource(R.string.edit),
        ){
            onEvent(MenuEvent.CloseDialog)
            when(position){
                is Position.PositionDraftView -> {
                    //nav
                }
                is Position.PositionIngredientView -> {
                    onEvent(MenuEvent.OpenDialog(DialogData.EditIngredient(position, stateBottom, onEvent)))
                }
                is Position.PositionNoteView -> {
                    onEvent(MenuEvent.OpenDialog(DialogData.EditNote( position, stateBottom, onEvent)))
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
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.OpenDialog(DialogData.MovePositionToMenu(state, position, onEvent)))
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ){
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.OpenDialog(DialogData.DoublePositionToMenu(state, position, onEvent)))
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ){
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.Delete(position)))
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
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
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
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
                    onEvent(MenuEvent.OpenDialog(DialogMenuData.EditIngredient(position, stateBottom)))
                }
                is Position.PositionNoteView -> {
                    onEvent(MenuEvent.OpenDialog(DialogMenuData.EditNote( position, stateBottom)))
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
            onEvent(MenuEvent.OpenDialog(DialogMenuData.MovePositionToMenu(state, position)))
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ){
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.OpenDialog(DialogMenuData.DoublePositionToMenu(state, position)))
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ){
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.ActionDBMenu(ActionDBData.Delete(position)))
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
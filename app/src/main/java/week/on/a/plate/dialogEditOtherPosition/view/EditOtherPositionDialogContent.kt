package week.on.a.plate.dialogEditOtherPosition.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.dialogEditOtherPosition.event.EditOtherPositionEvent
import week.on.a.plate.dialogEditRecipePosition.view.ButtonRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun EditOtherPositionDialogContent( onEvent: (EditOtherPositionEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        ButtonRow(
            R.drawable.edit,
            stringResource(R.string.edit),
        ){
            onEvent(EditOtherPositionEvent.Edit)

        }

        ButtonRow(
            R.drawable.back_key,
            stringResource(R.string.move),
        ){
            onEvent(EditOtherPositionEvent.Move)
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ){
            onEvent(EditOtherPositionEvent.Double)
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ){
            onEvent(EditOtherPositionEvent.Delete)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOtherPanel() {
    WeekOnAPlateTheme {
        EditOtherPositionDialogContent {}
    }
}
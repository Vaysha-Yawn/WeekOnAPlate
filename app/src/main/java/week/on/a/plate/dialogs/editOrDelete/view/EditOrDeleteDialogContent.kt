package week.on.a.plate.dialogs.editOrDelete.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.ButtonRow
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent

@Composable
fun EditOrDeleteDialogContent( onEvent: (EditOrDeleteEvent) -> Unit) {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface).padding(20.dp)) {
        ButtonRow(
            R.drawable.edit,
            text=stringResource(R.string.edit),
        ){
            onEvent(EditOrDeleteEvent.Edit)
        }

        ButtonRow(
            R.drawable.delete,
            text=stringResource(R.string.delete),
        ){
            onEvent(EditOrDeleteEvent.Delete)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditOrDelete() {
    WeekOnAPlateTheme {
        EditOrDeleteDialogContent {}
    }
}
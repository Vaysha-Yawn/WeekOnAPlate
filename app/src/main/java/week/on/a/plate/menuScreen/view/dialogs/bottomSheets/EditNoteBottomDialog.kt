package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionNoteExample
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.eventData.ActionDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun AddNoteBottomDialogContent(
    data: DialogMenuData.AddNote,
    onEvent: (MenuEvent) -> Unit
) {
    EditOrAddNoteBottomDialogContent(
        stringResource(R.string.add_note),
        stringResource(id = R.string.add),
        data.text){
        onEvent(MenuEvent.ActionDBMenu(ActionDBData.AddNoteDB(data)))
        onEvent(MenuEvent.CloseDialog)
    }
}

@Composable
fun EditNoteBottomDialogContent(
    data: DialogMenuData.EditNote,
    onEvent: (MenuEvent) -> Unit
) {
    EditOrAddNoteBottomDialogContent(
        stringResource(R.string.edit_note),
        stringResource(R.string.apply),
        data.text){
        onEvent(MenuEvent.ActionDBMenu(ActionDBData.EditNoteDB(data)))
        onEvent(MenuEvent.CloseDialog)
    }
}

@Composable
fun EditOrAddNoteBottomDialogContent(
    title:String,
    doneButtonText:String,
    text: MutableState<String>,
    done:()->Unit
) {
    Column(Modifier.padding(24.dp)) {
        EditTextLine(
            text,
            title,
            stringResource(R.string.enter_text_note),
        ) { text.value = it }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = doneButtonText) { done() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewEditNoteBottomDialog() {
    WeekOnAPlateTheme {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        EditNoteBottomDialogContent(DialogMenuData.EditNote(positionNoteExample, stateBottom)) {}
    }
}
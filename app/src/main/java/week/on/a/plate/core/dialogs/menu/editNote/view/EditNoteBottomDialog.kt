package week.on.a.plate.core.dialogs.menu.editNote.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun EditNoteBottomDialogContent(
    text: MutableState<String>,
    done: () -> Unit
) {
    Column(Modifier.padding(24.dp)) {
        EditTextLine(
            text,
            stringResource(R.string.title_edit_note),
            stringResource(R.string.enter_text_note),
        ) { text.value = it }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = stringResource(R.string.apply)) { done() }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditNoteBottomDialog() {
    WeekOnAPlateTheme {
        val text = remember {
            mutableStateOf("")
        }
        EditNoteBottomDialogContent(text){}
    }
}
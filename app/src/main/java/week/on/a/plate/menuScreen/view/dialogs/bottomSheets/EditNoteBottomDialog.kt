package week.on.a.plate.menuScreen.view.dialogs.bottomSheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

//todo хранить состояния в state, существлять действия с MenuEvent,
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNoteBottomDialog(
    //note: Position.PositionNoteView,
    state: MenuIUState,
    onEvent: (MenuEvent) -> Unit
) {
    val stateD = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val text = remember { mutableStateOf("") }
    BottomDialogContainer(stateD) {
        EditNoteBottomDialogContent(text, state, onEvent) {
            CoroutineScope(Dispatchers.Main).launch {
                stateD.expand()
            }
        }
    }
}

@Composable
fun EditNoteBottomDialogContent(
    //note: Position.PositionNoteView,
    text: MutableState<String>,
    state: MenuIUState,
    onEvent: (MenuEvent) -> Unit,
    clickToDone: () -> Unit
) {
    Column(Modifier.padding(24.dp)) {
        EditTextLine(
            text,
            "Добавить заметку",
            "Введите текст заметки",
        ) {}
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(text = "Добавить") {
            //onEvent(MenuEvent.AddNote(text.value))
            //onEvent(MenuEvent.EditNote(text.value))
            //onEvent(MenuEvent.CloseAddNoteDialog)
            clickToDone()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEditNoteBottomDialog() {
    WeekOnAPlateTheme {
        val uiState = MenuIUState.MenuIUStateExample
        val text = remember { mutableStateOf("") }
        EditNoteBottomDialogContent(text, uiState, {}) {}
    }
}
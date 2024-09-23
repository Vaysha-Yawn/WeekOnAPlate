package week.on.a.plate.core.dialogs.menu.editNote.state

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.week.Position

class EditNoteUIState (
    val note: Position.PositionNoteView?,
){
    val text = mutableStateOf(note?.note?:"")
}



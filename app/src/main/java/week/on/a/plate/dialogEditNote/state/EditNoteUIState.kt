package week.on.a.plate.dialogEditNote.state

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.Position

class EditNoteUIState (
    val note: Position.PositionNoteView?,
){
    val text = mutableStateOf(note?.note?:"")
}



package week.on.a.plate.dialogEditNote.event

import week.on.a.plate.core.Event

sealed class EditNoteEvent: Event() {
    data object Done: EditNoteEvent()
    data object Close: EditNoteEvent()
}
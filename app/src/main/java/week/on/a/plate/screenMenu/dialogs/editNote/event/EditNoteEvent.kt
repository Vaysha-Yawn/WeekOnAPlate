package week.on.a.plate.screenMenu.dialogs.editNote.event

import week.on.a.plate.core.Event

sealed class EditNoteEvent: Event() {
    data object Done: EditNoteEvent()
    data object Close: EditNoteEvent()
}
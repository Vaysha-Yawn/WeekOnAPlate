package week.on.a.plate.core.dialogs.menu.editNote.event

import week.on.a.plate.core.mainView.mainViewModelLogic.Event

sealed class EditNoteEvent:Event() {
    data object Done: EditNoteEvent()
    data object Close: EditNoteEvent()
}
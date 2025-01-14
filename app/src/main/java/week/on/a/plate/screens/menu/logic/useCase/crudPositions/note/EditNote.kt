package week.on.a.plate.screens.menu.logic.useCase.crudPositions.note

import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class EditNote @Inject constructor() {
    suspend operator fun invoke(
        note: Position.PositionNoteView, mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                note.note,
                R.string.edit_note,
                R.string.enter_text_note
            )
        ) { updatedNote ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.EditNoteDB(
                        Position.PositionNoteView(
                            note.id,
                            updatedNote,
                            note.selectionId
                        )
                    )
                )
            )
        }
    }
}
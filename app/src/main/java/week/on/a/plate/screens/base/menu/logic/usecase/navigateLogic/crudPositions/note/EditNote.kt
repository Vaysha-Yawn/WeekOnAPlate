package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.note

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.EditNoteInDBUseCase
import javax.inject.Inject

class EditNote @Inject constructor(
    private val editNote: EditNoteInDBUseCase
) {
    suspend operator fun invoke(
        note: Position.PositionNoteView, mainViewModel: MainViewModel,
    ) = coroutineScope {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                note.note,
                R.string.edit_note,
                R.string.enter_text_note
            )
        ) { updatedNote ->
            launch(Dispatchers.IO) {
                editNote(
                    Position.PositionNoteView(
                        note.id,
                        updatedNote,
                        note.selectionId
                    )
                )
            }
        }
    }
}
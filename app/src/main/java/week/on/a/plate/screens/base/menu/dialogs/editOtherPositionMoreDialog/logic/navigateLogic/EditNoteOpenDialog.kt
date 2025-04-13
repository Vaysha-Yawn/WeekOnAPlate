package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.navigateLogic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.base.menu.domain.dbusecase.EditNoteInDBUseCase
import javax.inject.Inject

class EditNoteOpenDialog @Inject constructor(
    private val editNote: EditNoteInDBUseCase
) {
    suspend operator fun invoke(
        note: Position.PositionNoteView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        val params = EditOneStringViewModel.EditOneStringDialogParams(
            EditOneStringUIState(
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

        dialogOpenParams.value = params
    }
}
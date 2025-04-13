package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddNoteToDBUseCase
import javax.inject.Inject

class CreateNoteOpenDialog @Inject constructor(
    private val addNote: AddNoteToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long, dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        val params = EditOneStringViewModel.EditOneStringDialogParams(
            EditOneStringUIState(
                "",
                R.string.add_note,
                R.string.enter_text_note
            )
        ) { newNote ->
            withContext(Dispatchers.IO) {
                addNote(newNote, selId)
            }
        }
        dialogOpenParams.value = params
    }
}
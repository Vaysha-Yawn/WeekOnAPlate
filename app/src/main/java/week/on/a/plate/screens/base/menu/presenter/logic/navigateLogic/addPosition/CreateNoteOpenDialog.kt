package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.editOneString.logic.EditOneStringViewModel
import week.on.a.plate.dialogs.editOneString.state.EditOneStringUIState
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddNoteToDBUseCase
import javax.inject.Inject

class CreateNoteOpenDialog @Inject constructor(
    private val addNote: AddNoteToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long, mainViewModel: MainViewModel
    ) = coroutineScope {
        EditOneStringViewModel.launch(
            mainViewModel, EditOneStringUIState(
                "",
                R.string.add_note,
                R.string.enter_text_note
            )
        ) { newNote ->
            launch(Dispatchers.IO) {
                addNote(newNote, selId)
            }
        }
    }
}
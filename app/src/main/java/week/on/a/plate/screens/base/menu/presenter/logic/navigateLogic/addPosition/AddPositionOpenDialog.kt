package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.supervisorScope
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.navigation.SearchDestination
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import javax.inject.Inject

class AddPositionOpenDialog @Inject constructor(
    private val createNoteOpenDialog: CreateNoteOpenDialog,
    private val createDraftNavToScreen: CreateDraftNavToScreen,
    private val addIngredientOpenDialog: AddIngredientOpenDialog,
) {
    suspend operator fun invoke(
        selId: Long,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit
    ) = supervisorScope {
        val params = AddPositionViewModel.AddPositionDialogParams { event ->
            when (event) {
                AddPositionEvent.AddDraft -> createDraftNavToScreen(selId, onEvent)
                AddPositionEvent.AddIngredient -> addIngredientOpenDialog(selId, dialogOpenParams)
                AddPositionEvent.AddNote -> createNoteOpenDialog(selId, dialogOpenParams)
                AddPositionEvent.AddRecipe -> onEvent(
                    MainEvent.Navigate(
                        SearchDestination(
                            selId,
                            null
                        )
                    )
                )
                AddPositionEvent.Close -> {}
            }
        }
        dialogOpenParams.value = params
    }
}
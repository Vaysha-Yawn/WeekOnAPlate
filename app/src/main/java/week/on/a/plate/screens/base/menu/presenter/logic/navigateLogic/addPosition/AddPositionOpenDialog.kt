package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import android.content.Context
import androidx.compose.runtime.MutableState
import kotlinx.coroutines.supervisorScope
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import javax.inject.Inject

class AddPositionOpenDialog @Inject constructor(
    private val createNoteOpenDialog: CreateNoteOpenDialog,
    private val addRecipeNavToScreen: AddRecipeNavToScreen,
    private val createDraftNavToScreen: CreateDraftNavToScreen,
    private val addIngredientOpenDialog: AddIngredientOpenDialog,
) {
    suspend operator fun invoke(
        selId: Long, context: Context,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        onEvent: (Event) -> Unit
    ) = supervisorScope {
        val params = AddPositionViewModel.AddPositionDialogParams { event ->
            when (event) {
                AddPositionEvent.AddDraft -> createDraftNavToScreen(selId, onEvent)
                AddPositionEvent.AddIngredient -> addIngredientOpenDialog(selId, dialogOpenParams)
                AddPositionEvent.AddNote -> createNoteOpenDialog(selId, dialogOpenParams)
                AddPositionEvent.AddRecipe -> addRecipeNavToScreen(
                    selId,
                    context, this,
                    dialogOpenParams, onEvent,
                )

                AddPositionEvent.Close -> {}
            }
        }
        dialogOpenParams.value = params
    }
}
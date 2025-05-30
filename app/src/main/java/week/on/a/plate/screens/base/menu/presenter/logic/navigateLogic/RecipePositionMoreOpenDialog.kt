package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.EditRecipePositionViewModel
import week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.RecipePositionDialogActionsMore
import javax.inject.Inject

class RecipePositionMoreOpenDialog @Inject constructor(
    private val recipePositionDialogActionsMore: RecipePositionDialogActionsMore,
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        onMenuEvent: (Event) -> Unit
    ) = coroutineScope {
        val params = EditRecipePositionViewModel.EditRecipePositionDialogParams() { event ->
            scope.launch {
                recipePositionDialogActionsMore(
                    position, dialogOpenParams,
                    event, scope, onMenuEvent
                )
            }
        }
        dialogOpenParams.value = params
    }
}
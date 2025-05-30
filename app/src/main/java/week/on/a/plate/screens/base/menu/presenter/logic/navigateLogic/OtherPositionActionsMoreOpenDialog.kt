package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic


import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.EditOtherPositionViewModel
import week.on.a.plate.dialogs.editOtherPositionMoreDialog.logic.OtherPositionActionsMore
import javax.inject.Inject

class OtherPositionActionsMoreOpenDialog @Inject constructor(
    private val otherPositionActionsMore: OtherPositionActionsMore,
) {
    suspend operator fun invoke(
        position: Position,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
        onEvent: (Event) -> Unit,
    ) = coroutineScope {
        val params = EditOtherPositionViewModel.EditOtherPositionDialogParams(
            position is Position.PositionIngredientView,
        ) { event ->
            scope.launch(Dispatchers.IO) {
                otherPositionActionsMore(position, dialogOpenParams, onEvent, scope, event)
            }
        }
        dialogOpenParams.value = params
    }
}
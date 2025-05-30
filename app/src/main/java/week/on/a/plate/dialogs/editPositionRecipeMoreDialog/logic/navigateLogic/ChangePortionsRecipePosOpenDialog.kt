package week.on.a.plate.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.screens.base.menu.domain.dbusecase.ChangePortionsRecipePosInDBUseCase
import javax.inject.Inject

class ChangePortionsRecipePosOpenDialog @Inject constructor(
    private val changePortionsCount: ChangePortionsRecipePosInDBUseCase
) {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        scope: CoroutineScope,
    ) = coroutineScope {
        val params = ChangePortionsCountViewModel.ChangePortionsCountDialogParams(
            recipe.portionsCount
        ) { portionsCount ->
            scope.launch(Dispatchers.IO) {
                changePortionsCount(
                    recipe,
                    portionsCount
                )
            }
        }
        dialogOpenParams.value = params
    }
}

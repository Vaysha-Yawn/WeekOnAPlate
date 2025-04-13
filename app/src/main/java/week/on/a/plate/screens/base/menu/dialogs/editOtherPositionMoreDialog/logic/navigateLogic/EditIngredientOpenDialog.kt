package week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.navigateLogic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.screens.base.menu.domain.dbusecase.UpdateIngredientPositionInDBUseCase
import javax.inject.Inject

class EditIngredientOpenDialog @Inject constructor(
    private val updateIngredient: UpdateIngredientPositionInDBUseCase
) {
    suspend operator fun invoke(
        ingredientPos: Position.PositionIngredientView,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) = coroutineScope {
        val params = EditPositionIngredientViewModel.EditPositionIngredientDialogParams(
            ingredientPos,
            false
        ) { updatedIngredient ->
            launch {
                updateIngredient(updatedIngredient)
            }
        }
        dialogOpenParams.value = params
    }
}
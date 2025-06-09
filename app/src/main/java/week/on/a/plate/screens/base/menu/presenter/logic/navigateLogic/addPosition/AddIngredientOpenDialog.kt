package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic.addPosition

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.screens.base.menu.domain.dbusecase.AddIngredientPositionToDBUseCase
import javax.inject.Inject

class AddIngredientOpenDialog @Inject constructor(
    private val addIngredient: AddIngredientPositionToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long, dialogOpenParams: MutableState<DialogOpenParams?>,
    ) {
        val params = EditPositionIngredientViewModel.EditPositionIngredientDialogParams(
            null,
            true
        ) { newIngredient ->
            withContext(Dispatchers.IO) {
                addIngredient(
                    newIngredient,
                    selId
                )
            }
        }
        dialogOpenParams.value = params
    }
}
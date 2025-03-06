package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.AddIngredientPositionToDBUseCase
import javax.inject.Inject

class AddIngredientOpenDialog @Inject constructor(
    private val addIngredient: AddIngredientPositionToDBUseCase
) {
    suspend operator fun invoke(
        selId: Long, mainViewModel: MainViewModel
    ) = coroutineScope {
        EditPositionIngredientViewModel.launch(null, true, mainViewModel) { newIngredient ->
            launch(Dispatchers.IO) {
                addIngredient(
                    newIngredient,
                    selId
                )
            }
        }
    }
}
package week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.crudPositions.ingredient

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.UpdateIngredientPositionInDBUseCase
import javax.inject.Inject

class EditIngredientOpenDialog @Inject constructor(
    private val updateIngredient: UpdateIngredientPositionInDBUseCase
) {
    suspend operator fun invoke(
        ingredientPos: Position.PositionIngredientView,
        mainViewModel: MainViewModel,
    ) = coroutineScope {
        EditPositionIngredientViewModel.launch(
            ingredientPos,
            false,
            mainViewModel
        ) { updatedIngredient ->
            launch {
                updateIngredient(updatedIngredient)
            }
        }
    }
}
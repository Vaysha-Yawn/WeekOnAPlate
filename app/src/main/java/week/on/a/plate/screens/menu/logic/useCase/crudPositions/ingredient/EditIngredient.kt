package week.on.a.plate.screens.menu.logic.useCase.crudPositions.ingredient

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class EditIngredient @Inject constructor() {
    suspend operator fun invoke(
        ingredientPos: Position.PositionIngredientView,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
        EditPositionIngredientViewModel.launch(
            ingredientPos,
            false,
            mainViewModel
        ) { updatedIngredient ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.EditIngredientPositionDB(
                        updatedIngredient
                    )
                )
            )
        }
    }
}
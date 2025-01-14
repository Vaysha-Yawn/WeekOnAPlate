package week.on.a.plate.screens.menu.logic.useCase.crudPositions.ingredient

import week.on.a.plate.dialogs.editIngredientInMenu.logic.EditPositionIngredientViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class CreateIngredient @Inject constructor() {
    //addIngredientPosition
    suspend operator fun invoke(
        selId: Long, mainViewModel: MainViewModel, onEvent: (MenuEvent) -> Unit
    ) {
        EditPositionIngredientViewModel.launch(null, true, mainViewModel) { newIngredient ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.AddIngredientPositionDB(
                        newIngredient,
                        selId
                    )
                )
            )
        }
    }
}
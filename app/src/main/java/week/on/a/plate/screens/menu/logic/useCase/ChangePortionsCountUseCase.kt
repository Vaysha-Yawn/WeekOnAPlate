package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import javax.inject.Inject

class ChangePortionsCountUseCase @Inject constructor() {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
        onEvent: (MenuEvent) -> Unit
    ) {
        ChangePortionsCountViewModel.launch(
            mainViewModel,
            recipe.portionsCount
        ) { portionsCount ->
            onEvent(
                MenuEvent.ActionDBMenu(
                    ActionWeekMenuDB.ChangePortionsCountDB(
                        recipe,
                        portionsCount
                    )
                )
            )
        }
    }
}

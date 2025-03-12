package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.EditRecipePositionViewModel
import week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.RecipePositionDialogActionsMore
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import javax.inject.Inject

class RecipePositionMoreOpenDialog @Inject constructor(
    private val recipePositionDialogActionsMore: RecipePositionDialogActionsMore,
) {
    suspend operator fun invoke(
        position: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
        onMenuEvent: (MenuEvent) -> Unit
    ) = coroutineScope {
        EditRecipePositionViewModel.launch(mainViewModel) { event ->
            launch {
                recipePositionDialogActionsMore(position, mainViewModel, event, onMenuEvent)
            }
        }
    }
}
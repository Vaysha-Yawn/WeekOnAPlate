package week.on.a.plate.screens.base.menu.presenter.logic.navigateLogic

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.EditOtherPositionViewModel
import week.on.a.plate.screens.base.menu.dialogs.editOtherPositionMoreDialog.logic.OtherPositionActionsMore
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import javax.inject.Inject

class OtherPositionActionsMoreOpenDialog @Inject constructor(
    private val otherPositionActionsMore: OtherPositionActionsMore,
) {
    suspend operator fun invoke(
        position: Position,
        mainViewModel: MainViewModel,
        onEventMain: (MainEvent) -> Unit,
        onEventMenu: (MenuEvent) -> Unit,
    ) = coroutineScope {
        EditOtherPositionViewModel.launch(
            position is Position.PositionIngredientView,
            mainViewModel
        ) { event ->
            launch {
                otherPositionActionsMore(position, mainViewModel, onEventMain, onEventMenu, event)
            }
        }
    }
}
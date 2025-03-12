package week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.dialogs.changePortions.logic.ChangePortionsCountViewModel
import week.on.a.plate.screens.base.menu.domain.dbusecase.ChangePortionsRecipePosInDBUseCase
import javax.inject.Inject

class ChangePortionsRecipePosOpenDialog @Inject constructor(
    private val changePortionsCount: ChangePortionsRecipePosInDBUseCase
) {
    suspend operator fun invoke(
        recipe: Position.PositionRecipeView,
        mainViewModel: MainViewModel
    ) = coroutineScope {
        ChangePortionsCountViewModel.launch(
            mainViewModel,
            recipe.portionsCount
        ) { portionsCount ->
            launch(Dispatchers.IO) {
                changePortionsCount(
                    recipe,
                    portionsCount
                )
            }
        }
    }
}

package week.on.a.plate.screens.menu.logic.useCase

import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import javax.inject.Inject

class AddRecipeToCookPlanUseCase @Inject constructor() {
    operator fun invoke(
        position: Position.PositionRecipeView,
        mainViewModel: MainViewModel,
    ) {
        mainViewModel.specifyRecipeToCookPlanViewModel.launchAndGet(position)
        mainViewModel.nav.navigate(SpecifyForCookPlanDestination)
    }
}
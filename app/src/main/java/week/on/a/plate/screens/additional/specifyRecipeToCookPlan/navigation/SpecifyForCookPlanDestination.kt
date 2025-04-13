package week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.app.mainActivity.event.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position


@Serializable
data object SpecifyForCookPlanDestination

class SpecifyForCookPlanNavParams(private val recipePos: Position.PositionRecipeView) : NavParams {
    override fun launch(vm: MainViewModel) {
        vm.specifyRecipeToCookPlanViewModel.launchAndGet(recipePos)
    }
}
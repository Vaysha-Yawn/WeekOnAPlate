package week.on.a.plate.screens.base.menu.dialogs.editPositionRecipeMoreDialog.logic.navigateLogic


import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanDestination
import week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation.SpecifyForCookPlanNavParams
import javax.inject.Inject

class NavToAddRecipeToCookPlan @Inject constructor() {
    operator fun invoke(
        position: Position.PositionRecipeView,
        onEvent: (Event) -> Unit
    ) {
        val params = SpecifyForCookPlanNavParams(position)
        onEvent(MainEvent.Navigate(SpecifyForCookPlanDestination, params))
    }
}
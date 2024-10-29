package week.on.a.plate.screens.cookPlanner.event

import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import java.time.LocalDate
import java.time.LocalTime

sealed class CookPlannerEvent : Event() {
    data class CheckStep(val step:CookPlannerStepView):CookPlannerEvent()
    data class ShowStepMore(val step:CookPlannerStepView):CookPlannerEvent()
    data class NavToFullStep(val step: CookPlannerStepView) : CookPlannerEvent()
}
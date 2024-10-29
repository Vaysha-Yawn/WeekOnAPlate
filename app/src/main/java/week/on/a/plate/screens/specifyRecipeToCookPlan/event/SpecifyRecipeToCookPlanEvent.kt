package week.on.a.plate.screens.specifyRecipeToCookPlan.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class SpecifyRecipeToCookPlanEvent():Event(){
    data object OpenTimePick:SpecifyRecipeToCookPlanEvent()
    data class SelectDate(val date:LocalDate):SpecifyRecipeToCookPlanEvent()
    data object SwitchStartEnd:SpecifyRecipeToCookPlanEvent()
    data object Done:SpecifyRecipeToCookPlanEvent()
    data object Close:SpecifyRecipeToCookPlanEvent()
}
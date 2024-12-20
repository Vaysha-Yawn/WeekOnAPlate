package week.on.a.plate.screens.specifyRecipeToCookPlan.event

import android.content.Context
import week.on.a.plate.core.Event
import java.time.LocalDate

sealed class SpecifyRecipeToCookPlanEvent():Event(){
    data class OpenTimePick(val context: Context):SpecifyRecipeToCookPlanEvent()
    data class SelectDate(val date:LocalDate):SpecifyRecipeToCookPlanEvent()
    data object SwitchStartEnd:SpecifyRecipeToCookPlanEvent()
    data object Done:SpecifyRecipeToCookPlanEvent()
    data object Close:SpecifyRecipeToCookPlanEvent()
}
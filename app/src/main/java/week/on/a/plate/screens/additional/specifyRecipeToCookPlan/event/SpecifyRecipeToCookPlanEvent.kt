package week.on.a.plate.screens.additional.specifyRecipeToCookPlan.event

import week.on.a.plate.core.Event
import java.time.LocalDate

sealed interface SpecifyRecipeToCookPlanEvent : Event {
    object OpenTimePick : SpecifyRecipeToCookPlanEvent
    class SelectDate(val date: LocalDate) : SpecifyRecipeToCookPlanEvent
    object SwitchStartEnd : SpecifyRecipeToCookPlanEvent
    object Done : SpecifyRecipeToCookPlanEvent
    object Close : SpecifyRecipeToCookPlanEvent
}
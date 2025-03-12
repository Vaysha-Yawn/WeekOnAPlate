package week.on.a.plate.screens.base.cookPlanner.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.dataView.CookPlannerStepView

sealed interface CookPlannerEvent : Event {
    class CheckStep(val step: CookPlannerStepView) : CookPlannerEvent
    class ShowStepMore(val groupView: CookPlannerGroupView, val context: Context) : CookPlannerEvent
    class NavToFullStep(val groupView: CookPlannerGroupView) : CookPlannerEvent
}
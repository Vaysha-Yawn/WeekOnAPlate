package week.on.a.plate.screens.base.cookPlanner.event

import android.content.Context
import week.on.a.plate.core.Event
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.dataView.CookPlannerStepView

sealed class CookPlannerEvent : Event() {
    data class CheckStep(val step: CookPlannerStepView) : CookPlannerEvent()
    data class ShowStepMore(val groupView: CookPlannerGroupView, val context: Context) :
        CookPlannerEvent()
    data class NavToFullStep(val groupView: CookPlannerGroupView) : CookPlannerEvent()
}
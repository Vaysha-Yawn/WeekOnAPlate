package week.on.a.plate.screens.additional.specifyRecipeToCookPlan.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.core.navigation.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.dataView.week.Position


@Serializable
data class SpecifyForCookPlanDestination(val recipeID: Long, val portionsCount: Int)
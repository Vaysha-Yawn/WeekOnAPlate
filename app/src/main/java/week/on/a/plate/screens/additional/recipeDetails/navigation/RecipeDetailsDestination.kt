package week.on.a.plate.screens.additional.recipeDetails.navigation

import kotlinx.serialization.Serializable
import week.on.a.plate.core.navigation.NavParams
import week.on.a.plate.app.mainActivity.logic.MainViewModel

@Serializable
data class RecipeDetailsDestination(val resId: Long, val portionsCount: Int?)
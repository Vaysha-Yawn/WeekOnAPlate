package week.on.a.plate.data.dataView

import week.on.a.plate.data.dataView.recipe.RecipeStepView
import java.time.LocalDateTime

data class CookPlannerStepView(
    val id: Long,
    val plannerGroupId: Long,
    val recipeId: Long,
    val recipeName: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val stepView: RecipeStepView,
    val checked: Boolean
)
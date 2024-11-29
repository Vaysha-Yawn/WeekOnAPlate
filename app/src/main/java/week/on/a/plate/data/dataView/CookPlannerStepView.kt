package week.on.a.plate.data.dataView

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
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
    val allRecipeIngredientsByPortions: List<IngredientInRecipeView>,
    val checked: Boolean,
    val portionsCount:Int,
    val stdPortionsCount:Int,
    val pinnedIngredientsByPortionsCount: List<IngredientInRecipeView>,
)
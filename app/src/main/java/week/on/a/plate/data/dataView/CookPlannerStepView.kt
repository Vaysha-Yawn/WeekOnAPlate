package week.on.a.plate.data.dataView

import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import java.time.LocalDateTime

data class CookPlannerStepView(
    val id: Long,
    val plannerGroupId: Long,
    val stepView: RecipeStepView,
    val checked: Boolean,
    val pinnedIngredientsByPortionsCount: List<IngredientInRecipeView>,
)

data class CookPlannerGroupView(
    val id: Long,
    val recipeId:Long,
    val start:LocalDateTime,
    val end:LocalDateTime,
    val recipeName:String,
    val portionsCount: Int,
    val steps:List<CookPlannerStepView>
)
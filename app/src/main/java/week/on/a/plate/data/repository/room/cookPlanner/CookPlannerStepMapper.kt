package week.on.a.plate.data.repository.room.cookPlanner

import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.recipe.RecipeStepView


class CookPlannerStepMapper() {
    fun CookPlannerStepRoom.roomToView(
        stepView: RecipeStepView,
        recipeIngredients: List<IngredientInRecipeView>,
        portionsCount: Int,
        stdPortionsCount: Int,
    ): CookPlannerStepView =
        CookPlannerStepView(
            id = id,
            plannerGroupId = plannerGroupId,
            stepView = stepView,
            checked = checked,
            pinnedIngredientsByPortionsCount = mapPinnedIngredients(
                recipeIngredients,
                stdPortionsCount,
                portionsCount,
                stepView.ingredientsPinnedId
            )
        )

    fun CookPlannerStepView.viewToRoom(): CookPlannerStepRoom =
        CookPlannerStepRoom(
            plannerGroupId = plannerGroupId,
            originalStepId = stepView.id,
            checked = checked,
        )
}

fun mapPinnedIngredients(
    allIngredients: List<IngredientInRecipeView>,
    stdPortions: Int,
    currentPortions: Int,
    ingredientsPinnedId: List<Long>
): List<IngredientInRecipeView> {
    return ingredientsPinnedId.map { id ->
        val ingr = allIngredients.find { it.ingredientView.ingredientId == id }!!.copy()
        val startIngredientCount = ingr.count
        if (startIngredientCount > 0) {
            ingr.count =
                (startIngredientCount.toFloat() / stdPortions.toFloat() * currentPortions).toInt()
        }
        ingr
    }
}
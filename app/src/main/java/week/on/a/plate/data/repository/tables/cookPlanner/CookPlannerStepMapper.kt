package week.on.a.plate.data.repository.tables.cookPlanner

import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeStepView


class CookPlannerStepMapper() {
    fun CookPlannerStepRoom.roomToView(
        recipeName: String, stepView: RecipeStepView
    ): CookPlannerStepView =
        CookPlannerStepView(
            id = id,
            plannerGroupId = plannerGroupId,
            recipeId = recipeId,
            recipeName = recipeName,
            start = start,
            end = end,
            stepView = stepView,
            checked = checked
        )

    fun CookPlannerStepView.viewToRoom(): CookPlannerStepRoom =
        CookPlannerStepRoom(
            recipeId = recipeId,
            plannerGroupId = plannerGroupId,
            originalStepId = stepView.id,
            checked = checked,
            start = start,
            end = end
        )
}

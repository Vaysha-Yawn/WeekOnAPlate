package week.on.a.plate.data.repository.tables.recipe.recipeStep

import week.on.a.plate.data.dataView.recipe.RecipeStepView
import javax.inject.Singleton

@Singleton
class StepRepository(
    private val daoStep: RecipeStepDAO,
) {
    private val stepMapper = RecipeStepMapper()

    suspend fun getSteps(recipeId: Long): List<RecipeStepView> {
        return daoStep.getRecipeAndRecipeSteps(recipeId).recipeStepRoom.map {
            with(stepMapper) { it.roomToView() }
        }
    }

    suspend fun insertSteps(list: List<RecipeStepView>, recipeId: Long) {
        val steps = list.map { with(stepMapper) { it.viewToRoom(recipeId) } }
        steps.forEach { daoStep.insert(it) }
    }

    suspend fun deleteSteps(list: List<RecipeStepView>, recipeId: Long) {
        list.forEach { daoStep.deleteByIdStep(it.id) }
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        daoStep.deleteByIdStep(recipeId)
    }
}
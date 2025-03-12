package week.on.a.plate.data.repository.room.recipe.recipeStep

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepRepository @Inject constructor(
    private val daoStep: RecipeStepDAO,
) {
    private val stepMapper = RecipeStepMapper()

    suspend fun getSteps(recipeId: Long): List<RecipeStepView> {
        return daoStep.getRecipeAndRecipeSteps(recipeId).recipeStepRoom.map {
            with(stepMapper) { it.roomToView() }
        }
    }

    suspend fun getStep(stepId: Long): RecipeStepView? {
        return with(stepMapper) { daoStep.getStepById(stepId)?.roomToView() }
    }

    fun getStepsFlow(recipeId: Long): Flow<List<RecipeStepView>> {
        return daoStep.getRecipeAndRecipeStepsFlow(recipeId).map { recipeAndRecipeSteps ->
            recipeAndRecipeSteps.recipeStepRoom.map { stepRoom ->
                with(stepMapper) { stepRoom.roomToView() }
            }
        }
    }

    suspend fun insertStep(stepView: RecipeStepView, recipeId: Long): Long {
        val stepRoom = with(stepMapper) { stepView.viewToRoom(recipeId) }
        return daoStep.insert(stepRoom)
    }

    suspend fun deleteStep(recipe: RecipeStepView) {
        daoStep.deleteByIdStep(recipe.id)
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        daoStep.deleteByIdStep(recipeId)
    }

    suspend fun update(step: RecipeStepView, recipeId: Long) {
        val stepRoom = with(stepMapper) { step.viewToRoom(recipeId) }.apply { this.id = step.id }
        daoStep.update(stepRoom)
    }
}
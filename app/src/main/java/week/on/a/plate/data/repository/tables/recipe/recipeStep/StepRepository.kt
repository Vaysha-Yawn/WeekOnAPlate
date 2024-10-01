package week.on.a.plate.data.repository.tables.recipe.recipeStep

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

    fun getStepsFlow(recipeId: Long): Flow<List<RecipeStepView>> {
        return daoStep.getRecipeAndRecipeStepsFlow(recipeId).map {recipeAndRecipeSteps->
            recipeAndRecipeSteps.recipeStepRoom.map {stepRoom->
                with(stepMapper) { stepRoom.roomToView() }
            }
        }
    }

    suspend fun insertSteps(list: List<RecipeStepView>, recipeId: Long) {
        val steps = list.map { with(stepMapper) { it.viewToRoom(recipeId) } }
        steps.forEach { daoStep.insert(it) }
    }

    suspend fun deleteStep(recipe: RecipeStepView) {
         daoStep.deleteByIdStep(recipe.id)
    }

    suspend fun deleteByRecipeId(recipeId: Long) {
        daoStep.deleteByIdStep(recipeId)
    }
}
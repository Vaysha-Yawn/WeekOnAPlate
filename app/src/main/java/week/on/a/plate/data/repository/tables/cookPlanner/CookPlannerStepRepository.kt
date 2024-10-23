package week.on.a.plate.data.repository.tables.cookPlanner


import androidx.compose.ui.util.fastForEachReversed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.data.repository.tables.recipe.recipeStep.StepRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject


class CookPlannerStepRepository @Inject constructor(
    private val groupRepo:CookPlannerGroupDAO,
    private val stepRepo:CookPlannerStepDAO,
    private val stepRecipeRepo:StepRepository,
    private val recipeRepo:RecipeRepository,
) {
    private val cookPlannerStepMapper = CookPlannerStepMapper()

    fun getAllByDate(date:LocalDate): Flow<List<CookPlannerStepView>> {
        return stepRepo.getAllFlowByDateStart(date.toString()).map {listCookPlannerStepRoom->
            listCookPlannerStepRoom.map {cookPlannerStepRoom->
                val recipeName = recipeRepo.getRecipe(cookPlannerStepRoom.recipeId).name
                val step = stepRecipeRepo.getStep(cookPlannerStepRoom.originalStepId)
                with(cookPlannerStepMapper){
                    cookPlannerStepRoom.roomToView(recipeName, step)
                }
            }.sortedBy { it.start }
        }
    }

    suspend fun insertGroupByStart(recipe:RecipeView, start:LocalDateTime ) {
        val groupId = groupRepo.insert(CookPlannerGroupRoom(recipe.id))
        var time = start
        recipe.steps.forEach {stepView->
            val stepRoom = CookPlannerStepRoom(recipe.id, groupId, stepView.id, false, time, time.with(stepView.duration))
            time = time.with(stepView.duration)
            stepRepo.insert(stepRoom)
        }
    }

    suspend fun insertGroupByEnd(recipe:RecipeView, end:LocalDateTime ) {
        val groupId = groupRepo.insert(CookPlannerGroupRoom(recipe.id))
        var time = end
        recipe.steps.fastForEachReversed { stepView ->
            val startTime =  time.minusHours(stepView.duration.hour.toLong()).minusMinutes(stepView.duration.minute.toLong())
            val stepRoom = CookPlannerStepRoom(recipe.id, groupId, stepView.id, false,startTime, time)
            time = startTime
            stepRepo.insert(stepRoom)
        }
    }

    suspend fun extendStep(
        id: Long,
    ) {
        /*shoppingItemDAO.update(
            ShoppingItemRoom(ingredientInRecipeId, checked).apply {
                this.id = id
            }
        )
        ingredientInRecipeDAO.update(
            IngredientInRecipeRoom(
                recipeId = 0,
                ingredientId = ingredientId,
                description = description,
                count = count
            ).apply {
                this.id = ingredientInRecipeId
            })*/
    }

    suspend fun reduceStepToNow(){}
    suspend fun check(nowChecked:Boolean){

    }

    suspend fun deleteGroup(idGroup: Long) {
        groupRepo.deleteById(idGroup)
        stepRepo.deleteByIdGroup(idGroup)
    }
}

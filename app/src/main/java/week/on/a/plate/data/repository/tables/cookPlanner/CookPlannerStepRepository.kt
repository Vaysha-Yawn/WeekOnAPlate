package week.on.a.plate.data.repository.tables.cookPlanner


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.core.utils.getAllTimeCook
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.data.repository.tables.recipe.recipe.RecipeRepository
import week.on.a.plate.data.repository.tables.recipe.recipeStep.StepRepository
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CookPlannerStepRepository @Inject constructor(
    private val groupRepo: CookPlannerGroupDAO,
    private val stepRepo: CookPlannerStepDAO,
    private val stepRecipeRepo: StepRepository,
    private val recipeRepo: RecipeRepository,
) {
    private val cookPlannerStepMapper = CookPlannerStepMapper()

    fun getWeek(activeDate: LocalDate): MutableMap<LocalDate, Flow<List<CookPlannerStepView>>> {
        val week = mutableMapOf<LocalDate, Flow<List<CookPlannerStepView>>>()
        for (dayInWeek in DayOfWeek.entries) {
            val date =
                activeDate.plusDays(dayInWeek.ordinal.toLong() - activeDate.dayOfWeek.ordinal.toLong())
            val list = getAllByDate(date)
            week[date] = list
        }
        return week
    }

    private fun getAllByDate(date: LocalDate): Flow<List<CookPlannerStepView>> {
        return stepRepo.getAllFlowByDateStart(date.toString()).map { listCookPlannerStepRoom ->
            listCookPlannerStepRoom.map { cookPlannerStepRoom ->
                val group = groupRepo.getById(cookPlannerStepRoom.plannerGroupId)
                val recipe = recipeRepo.getRecipe(group.recipeId)
                val step = stepRecipeRepo.getStep(cookPlannerStepRoom.originalStepId)
                with(cookPlannerStepMapper) {
                    cookPlannerStepRoom.roomToView(
                        recipe.name,
                        step,
                        recipe.ingredients,
                        group.recipeId,
                        group.portionsCount,
                        recipe.standardPortionsCount
                    )
                }
            }.sortedBy { it.start }
        }
    }

    suspend fun getAllByDateNoFlow(date: LocalDate): List<CookPlannerStepView> {
        return stepRepo.getAllFlowByDateStartNoFlow(date.toString()).map { cookPlannerStepRoom ->
            val group = groupRepo.getById(cookPlannerStepRoom.plannerGroupId)
            val recipe = recipeRepo.getRecipe(group.recipeId)
            val step = stepRecipeRepo.getStep(cookPlannerStepRoom.originalStepId)
            with(cookPlannerStepMapper) {
                cookPlannerStepRoom.roomToView(
                    recipe.name,
                    step,
                    recipe.ingredients,
                    group.recipeId,
                    group.portionsCount, recipe.standardPortionsCount
                )
            }
        }.sortedBy { it.start }
    }

    suspend fun insertGroupByStart(recipe: RecipeView, start: LocalDateTime, portionsCount: Int?) {
        val groupId = groupRepo.insert(
            CookPlannerGroupRoom(
                recipe.id,
                portionsCount ?: recipe.standardPortionsCount, start
            )
        )
        recipe.steps.forEach { stepView ->
            val stepRoom = CookPlannerStepRoom(
                groupId,
                stepView.id,
                false,
                start.plusSeconds(stepView.start),
                start.plusSeconds(stepView.duration).plusSeconds(stepView.start),
            )
            stepRepo.insert(stepRoom)
        }
    }

    suspend fun insertGroupByEnd(recipe: RecipeView, end: LocalDateTime, portionsCount: Int?) {
        val maxTime = recipe.getAllTimeCook().toLong()
        val start = end.minusSeconds(maxTime)
        val groupId = groupRepo.insert(
            CookPlannerGroupRoom(
                recipe.id,
                portionsCount ?: recipe.standardPortionsCount, start
            )
        )
        recipe.steps.forEach { stepView ->
            val stepRoom =
                CookPlannerStepRoom(
                    groupId,
                    stepView.id,
                    false,
                    start.plusSeconds(stepView.start),
                    start.plusSeconds(stepView.duration)
                        .plusSeconds(stepView.start),
                )
            stepRepo.insert(stepRoom)
        }
    }

    suspend fun check(step: CookPlannerStepView) {
        val stepRoom = with(cookPlannerStepMapper) {
            step.viewToRoom()
        }.apply {
            id = step.id
            checked = !step.checked
        }
        stepRepo.update(stepRoom)
    }

    suspend fun deleteGroup(idGroup: Long) {
        groupRepo.deleteById(idGroup)
        stepRepo.deleteByIdGroup(idGroup)
    }

    suspend fun changePortionsCount(step: CookPlannerStepView, newPortionsCount: Int) {
        val group = groupRepo.getById(step.plannerGroupId)
        group.portionsCount = newPortionsCount
        groupRepo.update(group)
    }

    suspend fun increaseStepTime(step: CookPlannerStepView, sec: Long) {
        val stepRoom = stepRepo.getById(step.id)
        stepRoom.end = stepRoom.end.plusSeconds(sec)
        stepRepo.update(stepRoom)
    }

    suspend fun moveStepByTimeStart(step: CookPlannerStepView, sec: Long) {
        val stepRoom = stepRepo.getById(step.id)
        stepRoom.start = stepRoom.start.plusSeconds(sec)
        stepRoom.end = stepRoom.end.plusSeconds(sec)
        stepRepo.update(stepRoom)
    }

    suspend fun changeStartRecipeTime(plannerGroupId: Long, start: Long) {
        val steps = stepRepo.getByGroupId(plannerGroupId)
        val group = groupRepo.getById(plannerGroupId)
        val stepsInRecipe = stepRecipeRepo.getSteps(group.recipeId)
        val startDateTime = LocalDateTime.of(
            group.start.toLocalDate(),
            LocalTime.ofSecondOfDay(start)
        )
        group.start = startDateTime
        groupRepo.update(group)
        stepsInRecipe.forEachIndexed() { ind, stepView ->
            val stepRoom = steps[ind]
            stepRoom.start = startDateTime.plusSeconds(stepView.start)
            stepRoom.end = startDateTime.plusSeconds(stepView.start + stepView.duration)
            stepRepo.update(stepRoom)
        }
    }

    suspend fun changeEndRecipeTime(plannerGroupId: Long, end: Long) {
        val steps = stepRepo.getByGroupId(plannerGroupId)
        val group = groupRepo.getById(plannerGroupId)
        val stepsInRecipe = stepRecipeRepo.getSteps(group.recipeId)

        val maxTime = stepsInRecipe.maxOf { it.start + it.duration }
        val start = end - maxTime

        val startDateTime = LocalDateTime.of(
            group.start.toLocalDate(),
            LocalTime.ofSecondOfDay(start)
        )
        group.start = startDateTime
        groupRepo.update(group)
        stepsInRecipe.forEachIndexed() { ind, stepView ->
            val stepRoom = steps[ind]
            stepRoom.start = startDateTime.plusSeconds(stepView.start)
            stepRoom.end = startDateTime.plusSeconds(stepView.start + stepView.duration)
            stepRepo.update(stepRoom)
        }
    }
}

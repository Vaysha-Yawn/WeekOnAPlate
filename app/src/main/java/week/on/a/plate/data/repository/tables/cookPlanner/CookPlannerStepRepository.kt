package week.on.a.plate.data.repository.tables.cookPlanner


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import week.on.a.plate.data.dataView.CookPlannerGroupView
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
    private val groupRepository: CookPlannerGroupDAO,
    private val stepRepo: CookPlannerStepDAO,
    private val stepRecipeRepo: StepRepository,
    private val recipeRepo: RecipeRepository,
) {
    private val cookPlannerStepMapper = CookPlannerStepMapper()

    fun getWeek(activeDate: LocalDate): MutableMap<LocalDate, Flow<List<CookPlannerGroupView>>> {
        val week = mutableMapOf<LocalDate, Flow<List<CookPlannerGroupView>>>()
        for (dayInWeek in DayOfWeek.entries) {
            val date =
                activeDate.plusDays(dayInWeek.ordinal.toLong() - activeDate.dayOfWeek.ordinal.toLong())
            val list = getAllByDate(date)
            week[date] = list
        }
        return week
    }

    private fun getAllByDate(date: LocalDate): Flow<List<CookPlannerGroupView>> {
        return groupRepository.getAllFlowByDateStart(date.toString()).map { listGroup ->
            listGroup.map { group ->
                val stepsGroup = stepRepo.getByGroupId(group.id)
                val recipe = recipeRepo.getRecipe(group.recipeId)
                val steps = stepsGroup.map { stepRoom ->
                    val stepRecipe = stepRecipeRepo.getStep(stepRoom.originalStepId)!!
                    with(cookPlannerStepMapper) {
                        stepRoom.roomToView(
                            stepRecipe,
                            recipe.ingredients,
                            group.portionsCount,
                            recipe.standardPortionsCount
                        )
                    }
                }.sortedBy { it.id }

                CookPlannerGroupView(
                    group.id,
                    group.recipeId,
                    group.start,
                    group.start.plusSeconds(recipe.duration.toSecondOfDay().toLong()),
                    recipe.name,
                    group.portionsCount,
                    steps
                )
            }.sortedBy { it.start }
        }
    }

    suspend fun getAllByDateNoFlow(date: LocalDate): List<CookPlannerGroupView> {
        return groupRepository.getAllByDateStart(date.toString()).map { group ->
                val stepsGroup = stepRepo.getByGroupId(group.id)
                val recipe = recipeRepo.getRecipe(group.recipeId)
                val steps = stepsGroup.map { stepRoom ->
                    val stepRecipe = stepRecipeRepo.getStep(stepRoom.originalStepId)!!
                    with(cookPlannerStepMapper) {
                        stepRoom.roomToView(
                            stepRecipe,
                            recipe.ingredients,
                            group.portionsCount,
                            recipe.standardPortionsCount
                        )
                    }
                }.sortedBy { it.id }

                CookPlannerGroupView(
                    group.id,
                    group.recipeId,
                    group.start,
                    group.start.plusSeconds(recipe.duration.toSecondOfDay().toLong()),
                    recipe.name,
                    group.portionsCount,
                    steps
                )
            }.sortedBy { it.start }
        }

    suspend fun insertGroupByStart(recipe: RecipeView, start: LocalDateTime, portionsCount: Int?) {
        val groupId = groupRepository.insert(
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
            )
            stepRepo.insert(stepRoom)
        }
    }

    suspend fun insertGroupByEnd(recipe: RecipeView, end: LocalDateTime, portionsCount: Int?) {
        val maxTime = recipe.duration
        val start = end.minusSeconds(maxTime.toSecondOfDay().toLong())
        val groupId = groupRepository.insert(
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
        groupRepository.deleteById(idGroup)
        stepRepo.deleteByIdGroup(idGroup)
    }

    suspend fun changePortionsCount(group: CookPlannerGroupView, newPortionsCount: Int) {
        val groupRoom = groupRepository.getById(group.id)
        groupRoom.portionsCount = newPortionsCount
        groupRepository.update(groupRoom)
    }

    suspend fun changeStartRecipeTime(plannerGroupId: Long, start: Long) {
        val steps = stepRepo.getByGroupId(plannerGroupId)
        val group = groupRepository.getById(plannerGroupId)
        val stepsInRecipe = stepRecipeRepo.getSteps(group.recipeId)
        val startDateTime = LocalDateTime.of(
            group.start.toLocalDate(),
            LocalTime.ofSecondOfDay(start)
        )
        group.start = startDateTime
        groupRepository.update(group)
        stepsInRecipe.forEachIndexed() { ind, stepView ->
            val stepRoom = steps[ind]
            stepRepo.update(stepRoom)
        }
    }

    suspend fun changeEndRecipeTime(plannerGroupId: Long, end: Long) {
        val group = groupRepository.getById(plannerGroupId)
        val recipe = recipeRepo.getRecipe(group.recipeId)

        val maxTime = recipe.duration
        val start = end - maxTime.toSecondOfDay()

        val startDateTime = LocalDateTime.of(
            group.start.toLocalDate(),
            LocalTime.ofSecondOfDay(start)
        )
        group.start = startDateTime
        groupRepository.update(group)
    }
}

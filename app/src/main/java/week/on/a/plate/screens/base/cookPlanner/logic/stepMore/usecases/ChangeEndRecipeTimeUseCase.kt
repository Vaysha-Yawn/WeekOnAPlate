package week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases

import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import javax.inject.Inject

class ChangeEndRecipeTimeUseCase @Inject constructor(
    private val repository: CookPlannerStepRepository
) {
    suspend operator fun invoke(group: CookPlannerGroupView, time:Long) {
        repository.changeEndRecipeTime(group.id, time)
    }
}
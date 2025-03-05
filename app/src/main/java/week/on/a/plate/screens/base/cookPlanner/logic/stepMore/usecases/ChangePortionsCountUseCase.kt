package week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases

import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import javax.inject.Inject

class ChangePortionsCountUseCase @Inject constructor(
    private val repository: CookPlannerStepRepository) {

    suspend operator fun invoke(group: CookPlannerGroupView, portionsCount: Int) {
        repository.changePortionsCount(group, portionsCount)
    }
}
package week.on.a.plate.screens.base.cookPlanner.logic.stepMore.usecases

import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import javax.inject.Inject

class CheckStepUseCase @Inject constructor(
    private val repository: CookPlannerStepRepository) {
    suspend operator fun invoke(event: CookPlannerEvent.CheckStep) {
        repository.check(event.step)
    }
}
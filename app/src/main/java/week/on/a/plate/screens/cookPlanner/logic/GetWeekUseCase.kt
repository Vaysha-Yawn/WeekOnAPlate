package week.on.a.plate.screens.cookPlanner.logic

import kotlinx.coroutines.flow.Flow
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import java.time.LocalDate
import javax.inject.Inject

class GetWeekUseCase @Inject constructor(
    private val repository: CookPlannerStepRepository) {
    operator fun invoke(date:LocalDate): MutableMap<LocalDate, Flow<List<CookPlannerGroupView>>> {
        return repository.getWeek(date)
    }
}
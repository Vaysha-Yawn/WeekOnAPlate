package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import java.time.LocalDate
import javax.inject.Inject

class GetSelectionDateUseCase @Inject constructor(
    private val selectionRepository: WeekMenuRepository,
) {
    suspend operator fun invoke(
        selId: Long
    ): LocalDate {
        return selectionRepository.getSelectionDateById(selId)
    }
}
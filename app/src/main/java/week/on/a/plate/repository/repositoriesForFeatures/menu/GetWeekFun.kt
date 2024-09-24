package week.on.a.plate.repository.repositoriesForFeatures.menu

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform
import week.on.a.plate.core.data.week.DayView
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.data.week.SelectionView
import week.on.a.plate.core.data.week.WeekView
import week.on.a.plate.repository.tables.weekOrg.day.DayDAO
import week.on.a.plate.repository.tables.weekOrg.day.DayMapper
import week.on.a.plate.repository.tables.weekOrg.day.DayRoom
import week.on.a.plate.repository.tables.weekOrg.position.positionDraft.PositionDraftRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.repository.tables.weekOrg.position.positionNote.PositionNoteRepository
import week.on.a.plate.repository.tables.weekOrg.position.recipeInMenu.PositionRecipeRepository
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionAndRecipesInMenu
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionDAO
import week.on.a.plate.repository.tables.weekOrg.selectionInDay.SelectionMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekDAO
import week.on.a.plate.repository.tables.weekOrg.week.WeekMapper
import week.on.a.plate.repository.tables.weekOrg.week.WeekRoom
import java.time.LocalDate
import javax.inject.Inject

class GetWeekFun @Inject constructor(
    private val positionDraftRepository: PositionDraftRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,
    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO,
) {

    suspend fun getCurrentWeek(day: LocalDate): WeekView? {
        val today = dayDAO.findDay(day) ?: return null
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId) ?: return null

        val listDaysView = mutableListOf<DayView>()
        weekAndDays.days.forEach { currentDay ->
            val dayAndSelections = dayDAO.getDayAndSelection(currentDay.dayId)

            val listSelection = mutableListOf<SelectionView>()

            dayAndSelections.selections.forEach { sel ->
                val selectionAndRecipesInMenu =
                    selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
                val selectionView = mapSelectionB(selectionAndRecipesInMenu)
                listSelection.add(selectionView)
            }

            val dayy = with(DayMapper()) {
                currentDay.roomToView(listSelection)
            }

            listDaysView.add(dayy)
        }

        val weekSelectAndRecipesInMenu =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelectionB(weekSelectAndRecipesInMenu)

        val weekFlow = with(WeekMapper()) { weekAndDays.week.roomToView(weekSel, listDaysView) }

        return weekFlow
    }

    private suspend fun mapSelectionB(selectionAndRecipesInMenu: SelectionAndRecipesInMenu): SelectionView {

        val listPositionRecipe =
            positionRecipeRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionIngredient =
            positionIngredientRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionNote =
            noteRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val listPositionDraft =
            positionDraftRepository.getAllInSel(selectionAndRecipesInMenu.selectionRoom.selectionId)

        val targetList = mutableListOf<Position>()
        targetList.addAll(listPositionRecipe)
        targetList.addAll(listPositionIngredient)
        targetList.addAll(listPositionNote)
        targetList.addAll(listPositionDraft)

        val sel = with(SelectionMapper()) {
            selectionAndRecipesInMenu.selectionRoom.roomToView(targetList)
        }
        return sel
    }
}
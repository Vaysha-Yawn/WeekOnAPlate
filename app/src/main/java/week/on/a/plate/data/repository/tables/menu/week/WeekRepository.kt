package week.on.a.plate.data.repository.tables.menu.week

import week.on.a.plate.data.dataView.example.emptyDay
import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.DayInWeekData
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.data.repository.tables.menu.day.DayDAO
import week.on.a.plate.data.repository.tables.menu.day.DayMapper
import week.on.a.plate.data.repository.tables.menu.day.DayRoom
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.tables.menu.selection.SelectionAndRecipesInMenu
import week.on.a.plate.data.repository.tables.menu.selection.SelectionDAO
import week.on.a.plate.data.repository.tables.menu.selection.SelectionMapper
import week.on.a.plate.data.repository.tables.menu.selection.SelectionRoom
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeekRepository @Inject constructor(
    private val positionDraftRepository: PositionDraftRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,

    private val weekDAO: WeekDAO,
    private val dayDAO: DayDAO,
    private val selectionDAO: SelectionDAO,
) {
    private val selectionMapper = SelectionMapper()
    private val dayMapper = DayMapper()
    private val weekMapper = WeekMapper()

    suspend fun addEmptyDay(date: LocalDate) {
        var weekId: Long? = null
        DayOfWeek.entries.forEach {
            val day = date.plusDays((it.value - date.dayOfWeek.value).toLong())
            val dayInRoom = dayDAO.findDay(day)
            if (dayInRoom != null) {
                weekId = dayInRoom.weekId
            }
        }

        if (weekId == null) {
            val selWeekId =
                selectionDAO.insert(SelectionRoom(0, CategoriesSelection.ForWeek.fullName))
            weekId = weekDAO.insert(WeekRoom(selWeekId))
        }

        val dayId =
            dayDAO.insert(DayRoom(date, DayInWeekData.localeDateToDayInWeekData(date), weekId!!))
        emptyDay.forEach {
            val sel = with(selectionMapper) {
                it.viewToRoom(dayId)
            }
            selectionDAO.insert(sel)
        }
    }

    suspend fun insertNewWeek(week: WeekView) {

        val selRoom = with(selectionMapper) {
            week.selection.viewToRoom(0)
        }
        val selId = selectionDAO.insert(selRoom)

        val weekRoom = with(weekMapper) {
            week.viewToRoom(selId)
        }
        val weekId = weekDAO.insert(weekRoom)

        week.days.forEach { day ->
            val dayRoom = with(dayMapper) {
                day.viewToRoom(weekId)
            }
            val dayId = dayDAO.insert(dayRoom)

            day.selections.forEach { selection ->

                val selectionRoom = with(selectionMapper) {
                    selection.viewToRoom(dayId)
                }
                val selectionId = selectionDAO.insert(selectionRoom)

                selection.positions.forEach { position ->
                    when (position) {
                        is Position.PositionDraftView -> {
                            positionDraftRepository.insert(position, selectionId)
                        }

                        is Position.PositionIngredientView -> {
                            positionIngredientRepository.insert(position, selectionId)
                        }

                        is Position.PositionNoteView -> noteRepository.insert(position, selectionId)

                        is Position.PositionRecipeView -> positionRecipeRepository.insert(
                            position,
                            selectionId
                        )
                    }
                }
            }
        }
    }

    suspend fun getSelIdOrCreate(date: LocalDate, category: CategoriesSelection): Long {
        val day = dayDAO.findDay(date)
        if (day != null) {
            when (category) {
                CategoriesSelection.ForWeek -> {
                    val week = weekDAO.findWeek(day.weekId)
                    return week.selectionId
                }

                else -> {
                    val dayAndSelections = dayDAO.getDayAndSelection(day.dayId)
                    dayAndSelections.selections.forEach {
                        if (it.category == category.fullName) {
                            return it.selectionId
                        }
                    }
                    return selectionDAO.insert(SelectionRoom(day.dayId, category.fullName))
                }
            }
        } else {
            //create day
            val selWeekId =
                selectionDAO.insert(SelectionRoom(0, CategoriesSelection.ForWeek.fullName))
            val weekId = weekDAO.insert(WeekRoom(selWeekId))
            val dayId = dayDAO.insert(
                DayRoom(
                    date, DayInWeekData.localeDateToDayInWeekData(date), weekId
                )
            )
            return when (category) {
                CategoriesSelection.ForWeek -> selWeekId
                else -> selectionDAO.insert(SelectionRoom(dayId, category.fullName))
            }
        }
    }

    suspend fun getCurrentWeek(day: LocalDate): WeekView? {
        val today = dayDAO.findDay(day) ?: return null
        val weekAndDays = weekDAO.getWeekAndDay(today.weekId) ?: return null

        val listDaysView = weekAndDays.days.map { currentDay ->
            val listSelection =  dayDAO.getDayAndSelection(currentDay.dayId).selections.map { sel ->
                val selectionAndRecipesInMenu =
                    selectionDAO.getSelectionAndRecipesInMenu(sel.selectionId)
               mapSelectionB(selectionAndRecipesInMenu)
            }
            with(dayMapper) {
                currentDay.roomToView(listSelection)
            }
        }

        val weekSelectAndRecipesInMenu =
            selectionDAO.getSelectionAndRecipesInMenu(weekAndDays.week.selectionId)
        val weekSel = mapSelectionB(weekSelectAndRecipesInMenu)

        val weekFlow = with(weekMapper) { weekAndDays.week.roomToView(weekSel, listDaysView) }

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

        val sel = with(selectionMapper) {
            selectionAndRecipesInMenu.selectionRoom.roomToView(targetList)
        }
        return sel
    }
}
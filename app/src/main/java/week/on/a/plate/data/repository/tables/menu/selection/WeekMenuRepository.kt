package week.on.a.plate.data.repository.tables.menu.selection

import week.on.a.plate.data.dataView.week.CategoriesSelection
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.data.repository.tables.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.tables.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.tables.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.tables.menu.position.positionRecipe.PositionRecipeRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeekMenuRepository @Inject constructor(
    private val draftRepository: PositionDraftRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,

    private val selectionDAO: SelectionDAO,
) {
    private val selectionMapper = SelectionMapper()

    suspend fun getSelIdOrCreate(
        dateTime: LocalDateTime,
        isForWeek: Boolean,
        category: String,
        locale: Locale,
    ): Long {
        val calendar = Calendar.getInstance(locale)
        calendar.set(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth)
        val week = calendar.get(Calendar.WEEK_OF_YEAR)
        return if (isForWeek) {
            val sel = selectionDAO.findSelectionForWeek(week)
            sel?.id ?: selectionDAO.insert(SelectionRoom(category, dateTime, week, isForWeek))
        } else {
            val sel = selectionDAO.findSelectionForDayByName(dateTime.toLocalDate().toString(), category)
            sel?.id ?: selectionDAO.insert(SelectionRoom(category, dateTime, week, isForWeek))
        }
    }

    private fun getDaysOfWeek(oneDate: LocalDate, locale: Locale): MutableList<LocalDate> {
        val firstDayOfWeek = WeekFields.of(locale).firstDayOfWeek
        val lastDayOfWeek = firstDayOfWeek.minus(1)

        val startOfWeek = oneDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
        val endOfWeek = oneDate.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))

        val daysOfWeek = mutableListOf<LocalDate>()
        var currentDay = startOfWeek
        while (!currentDay.isAfter(endOfWeek)) {
            daysOfWeek.add(currentDay)
            currentDay = currentDay.plusDays(1)
        }
        return daysOfWeek
    }

    suspend fun getCurrentWeek(date: LocalDate, locale: Locale): WeekView {
        val calendar = Calendar.getInstance(locale)
        calendar.set(date.year, date.monthValue, date.dayOfMonth)
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

        val dayDates = getDaysOfWeek(date, locale)
        val dayViews = dayDates.map { dateDay ->
            val listSelections = getSelectionsByDate(dateDay)
            val listSuggest = listOf(
                CategoriesSelection.NonPosed, CategoriesSelection.Breakfast,
                CategoriesSelection.Lunch, CategoriesSelection.Snack,
                CategoriesSelection.Dinner,
            )
            for (i in listSuggest) {
                if (listSelections.find { it.name == i.fullName } == null) {
                    listSelections.add(
                        SelectionView(
                            0,
                            i.fullName,
                            LocalDateTime.of(dateDay, i.stdTime),
                            weekOfYear,
                            false,
                            mutableListOf(),
                        )
                    )
                }
            }
            DayView(dateDay, listSelections)
        }

        val roomSel = selectionDAO.findSelectionForWeek(weekOfYear)
        val weekSel = if (roomSel != null) {
            mapSelection(roomSel)
        } else {
            SelectionView(
                0,
                CategoriesSelection.ForWeek.fullName,
                LocalDateTime.of(date, CategoriesSelection.ForWeek.stdTime),
                weekOfYear,
                true,
                mutableListOf(),
            )
        }

        val week = WeekView(weekOfYear, weekSel, dayViews)

        return week
    }

    suspend fun getSelectionsByDate(date: LocalDate): MutableList<SelectionView> {
        return selectionDAO.findSelectionsForDay(date.toString()).map { sel ->
            mapSelection(sel)
        }.toMutableList()
    }

    private suspend fun mapSelection(selectionRoom: SelectionRoom): SelectionView {
        val selId = selectionRoom.id
        val listPositionRecipe =
            positionRecipeRepository.getAllInSel(selId)

        val listPositionIngredient =
            positionIngredientRepository.getAllInSel(selId)

        val listPositionNote =
            noteRepository.getAllInSel(selId)

        val listPositionDraft =
            draftRepository.getAllInSel(selId)

        val targetList = mutableListOf<Position>()
        targetList.addAll(listPositionRecipe)
        targetList.addAll(listPositionIngredient)
        targetList.addAll(listPositionNote)
        targetList.addAll(listPositionDraft)

        val sel = with(selectionMapper) {
            selectionRoom.roomToView(targetList)
        }
        return sel
    }

    suspend fun deleteSelection(sel: SelectionView) {
        selectionDAO.deleteById(sel.id)
        sel.positions.forEach { pos ->
            when (pos) {
                is Position.PositionDraftView -> draftRepository.delete(pos.id)
                is Position.PositionIngredientView -> positionIngredientRepository.delete(pos.id)
                is Position.PositionNoteView -> noteRepository.delete(pos.id)
                is Position.PositionRecipeView -> positionRecipeRepository.delete(pos.id)
            }
        }
    }

    suspend fun editSelection(sel: SelectionView, newName: String, time: LocalTime) {
        sel.name = newName
        sel.dateTime = LocalDateTime.of(sel.dateTime.toLocalDate(), time)
        val selRoom = with(selectionMapper) {
            sel.viewToRoom().apply {
                this.id = sel.id
            }
        }
        selectionDAO.update(selRoom)
    }

    suspend fun createSelection(
        date: LocalDate,
        newName: String,
        locale: Locale,
        isForWeek: Boolean,
        time:LocalTime
    ) {
        val calendar = Calendar.getInstance(locale)
        calendar.set(date.year, date.monthValue, date.dayOfMonth)
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val sel = SelectionRoom(newName, LocalDateTime.of(date, time) , weekOfYear, isForWeek)
        selectionDAO.insert(sel)
    }
}
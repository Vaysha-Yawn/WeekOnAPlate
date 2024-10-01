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
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeekRepository @Inject constructor(
    private val draftRepository: PositionDraftRepository,
    private val positionIngredientRepository: PositionIngredientRepository,
    private val noteRepository: PositionNoteRepository,
    private val positionRecipeRepository: PositionRecipeRepository,

    private val selectionDAO: SelectionDAO,
) {
    private val selectionMapper = SelectionMapper()

    suspend fun getSelIdOrCreate(
        date: LocalDate,
        isForWeek: Boolean,
        category: CategoriesSelection,
        locale: Locale
    ): Long {
        val calendar = Calendar.getInstance(locale)
        calendar.set(date.year, date.monthValue, date.dayOfMonth)
        val week = calendar.get(Calendar.WEEK_OF_YEAR)
        return if (isForWeek) {
            val sel = selectionDAO.findSelectionForWeek(week)
            sel?.id ?: selectionDAO.insert(SelectionRoom(category.fullName, date, week, isForWeek))
        } else {
            val sel = selectionDAO.findSelectionForDayByName(date, category.fullName)
            sel?.id ?: selectionDAO.insert(SelectionRoom(category.fullName, date, week, isForWeek))
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
            val listSelections = selectionDAO.findSelectionsForDay(dateDay).map { sel ->
                mapSelection(sel)
            }.toMutableList()
            if (listSelections.isEmpty()){
                listSelections.add(SelectionView(0, CategoriesSelection.NonPosed.fullName, dateDay, weekOfYear, false, mutableListOf()))
            }
            DayView(dateDay, listSelections)
        }

        val roomSel = selectionDAO.findSelectionForWeek(weekOfYear)
        val weekSel = if (roomSel!=null){
            mapSelection(roomSel)
        }else{
            SelectionView(0, CategoriesSelection.ForWeek.fullName, date, weekOfYear, true, mutableListOf())
        }

        val week = WeekView(weekOfYear, weekSel, dayViews)

        return week
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
}
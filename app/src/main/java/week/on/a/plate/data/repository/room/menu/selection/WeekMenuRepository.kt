package week.on.a.plate.data.repository.room.menu.selection

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import week.on.a.plate.data.dataView.week.DayView
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.NonPosed
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.data.dataView.week.WeekView
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.data.repository.room.menu.position.draft.PositionDraftRepository
import week.on.a.plate.data.repository.room.menu.position.note.PositionNoteRepository
import week.on.a.plate.data.repository.room.menu.position.positionIngredient.PositionIngredientRepository
import week.on.a.plate.data.repository.room.menu.position.positionRecipe.PositionRecipeRepository
import week.on.a.plate.data.repository.utils.combineSafeIfFlowIsEmpty
import week.on.a.plate.screens.base.menu.domain.repositoryInterface.ISelectionRepository
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
    private val categorySelectionDAO: CategorySelectionDAO,
) : ISelectionRepository {

    private val selectionMapper = SelectionMapper()

    override suspend fun getSelIdOrCreate(
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
            val sel =
                selectionDAO.findSelectionForDayByName(dateTime.toLocalDate().toString(), category)
            sel?.id ?: selectionDAO.insert(SelectionRoom(category, dateTime, week, isForWeek))
        }
    }

    private fun getWeekOfYear(locale: Locale, date: LocalDate): Int {
        val calendar = Calendar.getInstance(locale)
        calendar.set(date.year, date.monthValue, date.dayOfMonth)
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }

    suspend fun getCurrentWeek(
        nonPosedFullName: String,
        forWeekFullName: String,
        date: LocalDate,
        locale: Locale
    ): WeekView {
        val weekOfYear = getWeekOfYear(locale, date)
        val dayDates = getDaysOfWeek(date, locale)
        val dayViews = dayDates.map { dateDay ->
            var listSelections = getSelectionsByDate(dateDay)
            listSelections = addSuggest(nonPosedFullName, listSelections, dateDay, weekOfYear)
            DayView(dateDay, listSelections)
        }

        val roomSel = selectionDAO.findSelectionForWeek(weekOfYear)
        val weekSel = if (roomSel != null) {
            mapSelection(roomSel)
        } else {
            SelectionView(
                0,
                forWeekFullName,
                LocalDateTime.of(date, ForWeek.stdTime),
                weekOfYear,
                true,
                mutableListOf(),
            )
        }

        val week = WeekView(weekOfYear, weekSel, dayViews)

        return week
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getCurrentWeekFlow(
        nonPosedFullName: String,
        forWeekFullName: String,
        date: LocalDate,
        locale: Locale,
    ): Flow<WeekView> {
        val weekOfYear = getWeekOfYear(locale, date)
        val dayDates = getDaysOfWeek(date, locale)
        val dayViews = dayDates.map { dateDay ->
            val listSelections = getSelectionsByDateFlow(dateDay)
            val editedListSelections = listSelections.flatMapLatest {
                addSuggestFlow(nonPosedFullName, it.toMutableList(), dateDay, weekOfYear)
            }
            val flowDayView = editedListSelections.map {
                DayView(dateDay, it)
            }
            flowDayView
        }
        val flowDayViews = dayViews.combineSafeIfFlowIsEmpty()

        val weekSel = prepareWeekSelFlow(weekOfYear, forWeekFullName, date)

        return combine(weekSel, flowDayViews) { weekSeld, dayViewsd ->
            WeekView(weekOfYear, weekSeld, dayViewsd)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun prepareWeekSelFlow(
        weekOfYear: Int,
        forWeekFullName: String,
        date: LocalDate
    ) = selectionDAO.findSelectionForWeekFlow(weekOfYear).onStart { emit(null) }
        .flatMapLatest { roomSel ->
            if (roomSel != null) {
                mapSelectionFlow(roomSel)
            } else {
                flowOf(
                    SelectionView(
                        0,
                        forWeekFullName,
                        LocalDateTime.of(date, ForWeek.stdTime),
                        weekOfYear,
                        true,
                        mutableListOf(),
                    )
                )
            }
        }

    private suspend fun addSuggest(
        nonPosedFullName: String,
        listSelections: List<SelectionView>,
        dateDay: LocalDate,
        weekOfYear: Int
    ): List<SelectionView> {
        val result = listSelections.toMutableList()
        var listSuggest = categorySelectionDAO.getAll().toMutableList()
        listSuggest.add(CategorySelectionRoom(nonPosedFullName, NonPosed.stdTime))
        listSuggest = listSuggest.toMutableList()
        for (i in listSuggest) {
            if (listSelections.find { it.name == i.name } == null) {
                result.add(
                    SelectionView(
                        0,
                        i.name,
                        LocalDateTime.of(dateDay, i.stdTime),
                        weekOfYear,
                        false,
                        mutableListOf(),
                    )
                )
            }
        }
        return result.toList().sortedBy { it.dateTime }
    }

    private fun addSuggestFlow(
        nonPosedFullName: String,
        listSelections: List<SelectionView>,
        dateDay: LocalDate,
        weekOfYear: Int
    ): Flow<List<SelectionView>> {
        var flowSuggest = categorySelectionDAO.getAllFlow()
        flowSuggest = flowSuggest.map {
            it.toMutableList().apply {
                add(CategorySelectionRoom(nonPosedFullName, NonPosed.stdTime))
            }
        }
        val flowListSelections = flowSuggest.map { listSuggest ->
            val result = listSelections.toMutableList()
            for (i in listSuggest) {
                if (listSelections.find { it.name == i.name } == null) {
                    result.add(
                        SelectionView(
                            0,
                            i.name,
                            LocalDateTime.of(dateDay, i.stdTime),
                            weekOfYear,
                            false,
                            mutableListOf(),
                        )
                    )
                }
            }
            result.toList().sortedBy { it.dateTime }
        }
        return flowListSelections
    }

    suspend fun getSelectionsByDate(date: LocalDate): List<SelectionView> {
        return selectionDAO.findSelectionsForDay(date.toString()).map { sel ->
            mapSelection(sel)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSelectionsByDateFlow(date: LocalDate): Flow<List<SelectionView>> {
        return selectionDAO.findSelectionsForDayFlow(date.toString()).onEmpty { emit(emptyList()) }
            .flatMapLatest { selectionRoomList ->
                val listFlow = selectionRoomList.map { sel ->
                    mapSelectionFlow(sel)
                }
                val flow = listFlow.combineSafeIfFlowIsEmpty()
                flow
            }
    }

    private fun mapSelectionFlow(
        selectionRoom: SelectionRoom,
    ): Flow<SelectionView> {
        val selId = selectionRoom.id

        val listPositionRecipe = positionRecipeRepository.getAllInSelFlow(selId)
        val listPositionIngredient = positionIngredientRepository.getAllInSelFlow(selId)
        val listPositionNote = noteRepository.getAllInSelFlow(selId)
        val listPositionDraft = draftRepository.getAllInSelFlow(selId)

        val flowSelections = combine(
            listPositionRecipe, listPositionIngredient, listPositionNote, listPositionDraft
        ) { a, b, c, d ->
            val list = mutableListOf<Position>()
            list.addAll(a)
            list.addAll(b)
            list.addAll(c)
            list.addAll(d)
            list.toList()

            with(selectionMapper) {
                selectionRoom.roomToView(list)
            }
        }

        val empty = with(selectionMapper) {
            selectionRoom.roomToView(mutableListOf<Position>())
        }

        return flowSelections.onEmpty { emit(empty) }
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

    override suspend fun deleteSelection(sel: SelectionView) {
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

    override suspend fun editSelection(sel: SelectionView, newName: String, time: LocalTime) {
        sel.name = newName
        sel.dateTime = LocalDateTime.of(sel.dateTime.toLocalDate(), time)
        val selRoom = with(selectionMapper) {
            sel.viewToRoom().apply {
                this.id = sel.id
            }
        }
        selectionDAO.update(selRoom)
    }

    override suspend fun createSelection(
        date: LocalDate,
        newName: String,
        locale: Locale,
        isForWeek: Boolean,
        time: LocalTime
    ) {
        val calendar = Calendar.getInstance(locale)
        calendar.set(date.year, date.monthValue, date.dayOfMonth)
        val weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)
        val sel = SelectionRoom(newName, LocalDateTime.of(date, time), weekOfYear, isForWeek)
        selectionDAO.insert(sel)
    }
}

fun getDaysOfWeek(oneDate: LocalDate, locale: Locale): MutableList<LocalDate> {
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
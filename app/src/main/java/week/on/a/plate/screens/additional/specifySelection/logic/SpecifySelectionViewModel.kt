package week.on.a.plate.screens.additional.specifySelection.logic

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.NonPosed
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.room.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import week.on.a.plate.screens.additional.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.additional.specifySelection.state.SpecifySelectionUIState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

const val SpecifySelectionResultKey = "SpecifySelectionResultKey"

@HiltViewModel
class SpecifySelectionViewModel @Inject constructor(
    private val weekMenuRepository: WeekMenuRepository,
    private val calendarMyUseCase: CalendarMyUseCase,
    private val categorySelectionDAO: CategorySelectionDAO,
    private val addCustomSelectionUseCase: AddCustomSelectionUseCase,
) : ViewModel() {

    val state: SpecifySelectionUIState = SpecifySelectionUIState()
    var stateCalendar: StateCalendarMy = StateCalendarMy.emptyState

    val dialogOpenParams = mutableStateOf<DialogOpenParams?>(null)
    val mainEvent = mutableStateOf<MainEvent?>(null)

    init {
        val firstRow = calendarMyUseCase.getFirstRow(Locale.getDefault())
        stateCalendar.firstRow.value = firstRow
        val now = LocalDate.now()
        viewModelScope.launch {
            val allMonthDay = calendarMyUseCase.getAllMonthDays(now.year, now.monthValue, true)
            stateCalendar.allMonthDayAndIsPlanned.value = allMonthDay
        }
        updateSelections()
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainEvent.value = event
            }

            is CalendarMyEvent -> {
                calendarMyUseCase.onEvent(event, stateCalendar, true)
            }

            is SpecifySelectionEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: SpecifySelectionEvent) {
        when (event) {
            SpecifySelectionEvent.Back -> close()
            is SpecifySelectionEvent.Done -> done(event.context)
            is SpecifySelectionEvent.AddCustomSelection -> viewModelScope.launch {
                addCustomSelectionUseCase(
                    dialogOpenParams,
                    state,
                    viewModelScope
                )
            }
            is SpecifySelectionEvent.UpdatePreview -> updatePreview(event.date)
            is SpecifySelectionEvent.UpdateSelections -> updateSelections()
            is SpecifySelectionEvent.ApplyDate -> applyDate(event.date)
        }
    }


    private fun applyDate(date: LocalDate) {
        state.date.value = date
    }


    private fun updatePreview(date: LocalDate) {
        state.date.value = date
        viewModelScope.launch {
            val selections = weekMenuRepository.getSelectionsByDate(state.date.value)
            state.dayViewPreview.value = selections
        }
    }

    private fun updateSelections() {
        viewModelScope.launch {
            val allSelections = weekMenuRepository.getSelectionsByDate(state.date.value)
            val listSelName =
                allSelections.map { Pair(it.name, it.dateTime.toLocalTime()) }.toMutableList()
            var listSuggest = categorySelectionDAO.getAll().toMutableList()
            listSuggest.add(CategorySelectionRoom(state.nonPosedText, NonPosed.stdTime))
            listSuggest = listSuggest.sortedBy { it.stdTime }.toMutableList()
            for (i in listSuggest) {
                if (listSelName.find { it.first == i.name } == null) {
                    listSelName.add(
                        Pair(i.name, i.stdTime)
                    )
                }
            }
            state.allSelectionsIdDay.value = listSelName
        }
    }


    private fun getCategory(context: Context): Pair<String, LocalTime>? {
        if (!state.checkWeek.value && state.checkDayCategory.intValue == 0) return Pair(
            context.getString(
                NonPosed.fullName
            ), NonPosed.stdTime
        )
        if (state.checkWeek.value) return Pair(context.getString(ForWeek.fullName), ForWeek.stdTime)
        return state.allSelectionsIdDay.value[state.checkDayCategory.intValue]
    }

    fun done(context: Context) {
        val category = getCategory(context) ?: return
        val time = category.second
        val name = category.first
        viewModelScope.launch {
            val selId = if (!state.checkWeek.value) {
                weekMenuRepository.getSelIdOrCreate(
                    LocalDateTime.of(state.date.value, time),
                    state.checkWeek.value,
                    name,
                    Locale.getDefault(),
                )
            } else {
                weekMenuRepository.getSelIdOrCreate(
                    LocalDateTime.of(state.date.value, ForWeek.stdTime),
                    state.checkWeek.value,
                    name,
                    Locale.getDefault(),
                )
            }
            mainEvent.value = MainEvent.NavigateBackWithResult(SpecifySelectionResultKey, selId)
        }
    }

    fun close() {
        mainEvent.value = MainEvent.NavigateBack
    }
}
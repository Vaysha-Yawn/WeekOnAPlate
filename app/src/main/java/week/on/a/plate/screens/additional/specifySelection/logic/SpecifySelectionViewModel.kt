package week.on.a.plate.screens.additional.specifySelection.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuDestination
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
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SpecifySelectionViewModel @Inject constructor(
    private val weekMenuRepository: WeekMenuRepository,
    private val calendarMyUseCase: CalendarMyUseCase,
    private val categorySelectionDAO: CategorySelectionDAO,
    private val addCustomSelectionUseCase: AddCustomSelectionUseCase,
) : ViewModel() {
    lateinit var mainViewModel: MainViewModel
    val state: SpecifySelectionUIState = SpecifySelectionUIState()
    private lateinit var resultFlow: MutableStateFlow<SpecifySelectionResult?>
    var stateCalendar: StateCalendarMy = StateCalendarMy.emptyState

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
                mainViewModel.onEvent(event)
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
            is SpecifySelectionEvent.AddCustomSelection -> viewModelScope.launch {  addCustomSelectionUseCase(mainViewModel, state, viewModelScope) }
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
            val selections = weekMenuRepository.getSelectionsByDate(state.date.value!!)
            state.dayViewPreview.value = selections
        }
    }

    fun updateSelections() {
        viewModelScope.launch {
            val allSelections = weekMenuRepository.getSelectionsByDate(state.date.value)
            val listSelName = allSelections.map { it.name }.toMutableList()
            var listSuggest = categorySelectionDAO.getAll().toMutableList()
            listSuggest.add(CategorySelectionRoom(state.nonPosedText, NonPosed.stdTime))
            listSuggest = listSuggest.sortedBy { it.stdTime }.toMutableList()
            for (i in listSuggest) {
                if (listSelName.find { it == i.name } == null) {
                    listSelName.add(
                        i.name
                    )
                }
            }
            state.allSelectionsIdDay.value = listSelName
        }
    }


    private fun getCategory(context: Context): String? {
        if (!state.checkWeek.value && state.checkDayCategory.value == null) return null
        if (state.checkWeek.value) return context.getString(ForWeek.fullName)
        return state.checkDayCategory.value
    }

    fun done(context: Context) {
        val category = getCategory(context) ?: return
        mainViewModel.viewModelScope.launch {
            val selId = weekMenuRepository.getSelIdOrCreate(
                LocalDateTime.of(state.date.value, LocalTime.of(0, 0)),
                state.checkWeek.value,
                category,
                mainViewModel.locale,
            )
            resultFlow.value =
                SpecifySelectionResult(selId, state.date.value, state.portionsCount.intValue)
        }
    }

    fun close() {
        mainViewModel.onEvent(MainEvent.NavigateBack)
    }

    fun start(): Flow<SpecifySelectionResult?> {
        val flow = MutableStateFlow<SpecifySelectionResult?>(null)
        resultFlow = flow
        return flow
    }

    suspend fun launchAndGet(use: suspend (SpecifySelectionResult) -> Unit) {
        calendarMyUseCase.updateMonthValue(stateCalendar, true)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
                mainViewModel.menuViewModel.onEvent(WrapperDatePickerEvent.ChangeWeek(value.date))
                mainViewModel.nav.navigate(MenuDestination)
            }
        }
    }
}
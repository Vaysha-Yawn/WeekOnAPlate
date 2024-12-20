package week.on.a.plate.screens.specifySelection.logic

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.navigation.MenuDestination
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.NonPosed
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.data.repository.tables.menu.selection.WeekMenuRepository
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.specifySelection.event.SpecifySelectionEvent
import week.on.a.plate.screens.specifySelection.state.SpecifySelectionUIState
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class SpecifySelectionViewModel @Inject constructor(
    private val weekMenuRepository: WeekMenuRepository,
    private val calendarMyUseCase : CalendarMyUseCase,
    private val categorySelectionDAO: CategorySelectionDAO,
    ) : ViewModel() {
     lateinit var mainViewModel: MainViewModel
    val state: SpecifySelectionUIState = SpecifySelectionUIState()
    private lateinit var resultFlow: MutableStateFlow<SpecifySelectionResult?>
    var stateCalendar : StateCalendarMy = StateCalendarMy.emptyState

    init {
        val firstRow =  calendarMyUseCase.getFirstRow(Locale.getDefault())
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
            SpecifySelectionEvent.Done -> done()
            is SpecifySelectionEvent.AddCustomSelection -> addCustomSelection(event.context)
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

    private fun addCustomSelection(context: Context) {
        viewModelScope.launch {
            val vm = EditSelectionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(
                EditSelectionUIState(
                    startTitle = context.getString(R.string.add_meal),
                    startPlaceholder = context.getString(R.string.hint_breakfast)
                )
            ) { selState ->
                state.allSelectionsIdDay.value =
                    state.allSelectionsIdDay.value.toMutableList().apply {
                        add(selState.text.value)
                    }
                viewModelScope.launch {
                    weekMenuRepository.getSelIdOrCreate(LocalDateTime.of(state.date.value, selState.selectedTime.value), false, selState.text.value, mainViewModel.locale, )
                }
                state.checkWeek.value = false
                state.checkDayCategory.value = selState.text.value
            }
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        }
    }

    fun updateSelections() {
        viewModelScope.launch {
            val allSelections = weekMenuRepository.getSelectionsByDate(state.date.value)
            val listSelName = allSelections.map { it.name }.toMutableList()
            var listSuggest = categorySelectionDAO.getAll().toMutableList()
            listSuggest.add(CategorySelectionRoom(NonPosed.fullName, NonPosed.stdTime))
            listSuggest =  listSuggest.sortedBy { it.stdTime }.toMutableList()
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

    private fun getCategory(): String? {
        if (!state.checkWeek.value && state.checkDayCategory.value == null) return null
        if (state.checkWeek.value) return ForWeek.fullName
        return state.checkDayCategory.value
    }

    fun done() {
        close()
        val category = getCategory() ?: return
        mainViewModel.viewModelScope.launch {
            val selId = weekMenuRepository.getSelIdOrCreate(
                LocalDateTime.of(state.date.value, LocalTime.of(0,0)),
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
                mainViewModel.menuViewModel.updateWeek()
                mainViewModel.nav.navigate(MenuDestination)
            }
        }
    }
}
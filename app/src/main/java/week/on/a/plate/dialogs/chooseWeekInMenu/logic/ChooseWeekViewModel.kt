package week.on.a.plate.dialogs.chooseWeekInMenu.logic

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.core.Event
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.core.utils.dateToLocalDate
import week.on.a.plate.screens.calendarMy.event.CalendarMyEvent
import week.on.a.plate.screens.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.screens.calendarMy.state.StateCalendarMy
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject


class ChooseWeekViewModel @Inject constructor (
    private val calendarMyUseCase: CalendarMyUseCase,
    val mainViewModel: MainViewModel
) : DialogViewModel() {
    lateinit var state: ChooseWeekUIState
    private lateinit var resultFlow: MutableStateFlow<LocalDate?>
    var stateCalendar : StateCalendarMy = StateCalendarMy.emptyState
    private var isForMenu = true

    init {
        val firstRow =  calendarMyUseCase.getFirstRow(Locale.getDefault())
        stateCalendar.firstRow.value = firstRow
        val now = LocalDate.now()
        mainViewModel.viewModelScope.launch {
            val allMonthDay = calendarMyUseCase.getAllMonthDays(now.year, now.monthValue, isForMenu)
            stateCalendar.allMonthDayAndIsPlanned.value = allMonthDay
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun getLocalDate(state: ChooseWeekUIState): LocalDate? {
        return state.state.selectedDateMillis?.dateToLocalDate()
    }

    fun start(): Flow<LocalDate?> {
        val flow = MutableStateFlow<LocalDate?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        //val date = getLocalDate(state)
        resultFlow.value = stateCalendar.activeDate.value
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainViewModel.onEvent(event)
            }
            is CalendarMyEvent -> {
                calendarMyUseCase.onEvent(event, stateCalendar, isForMenu)
            }
            is ChooseWeekDialogEvent -> {
                onEvent(event)
            }
        }
    }

    fun onEvent(event: ChooseWeekDialogEvent) {
        when (event) {
            ChooseWeekDialogEvent.Close -> close()
            ChooseWeekDialogEvent.Done -> done()
        }
    }

    suspend fun launchAndGet(isForMenud:Boolean, use: (LocalDate) -> Unit) {
        isForMenu = isForMenud
        calendarMyUseCase.updateMonthValue(stateCalendar, isForMenud)

        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
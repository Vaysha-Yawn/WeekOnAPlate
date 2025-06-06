package week.on.a.plate.dialogs.chooseWeekInMenu.logic

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.dialogs.calendarMy.event.CalendarMyEvent
import week.on.a.plate.dialogs.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.dialogs.calendarMy.state.StateCalendarMy
import week.on.a.plate.dialogs.chooseWeekInMenu.event.ChooseWeekDialogEvent
import week.on.a.plate.dialogs.chooseWeekInMenu.state.ChooseWeekUIState
import java.time.LocalDate
import java.util.Locale
import javax.inject.Inject


class ChooseWeekViewModel @Inject constructor(
    private val calendarMyUseCase: CalendarMyUseCase,
    private val isForMenu: Boolean,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (LocalDate) -> Unit
) : DialogViewModel<LocalDate>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    lateinit var state: ChooseWeekUIState
    var stateCalendar: StateCalendarMy = StateCalendarMy.emptyState
    val mainEvent: MutableState<MainEvent?> = mutableStateOf(null)

    init {
        scope.launch {
            val firstRow = calendarMyUseCase.getFirstRow(Locale.getDefault())
            stateCalendar.firstRow.value = firstRow
            val now = LocalDate.now()
            val allMonthDay = calendarMyUseCase.getAllMonthDays(now.year, now.monthValue, isForMenu)
            stateCalendar.allMonthDayAndIsPlanned.value = allMonthDay
            calendarMyUseCase.updateMonthValue(stateCalendar, isForMenu)
        }
    }

    fun onEvent(event: Event) {
        when (event) {
            is MainEvent -> {
                mainEvent.value = event
            }

            is CalendarMyEvent -> calendarMyUseCase.onEvent(event, stateCalendar, isForMenu)

            is ChooseWeekDialogEvent -> onEvent(event)
        }
    }

    fun onEvent(event: ChooseWeekDialogEvent) {
        when (event) {
            ChooseWeekDialogEvent.Close -> close()
            is ChooseWeekDialogEvent.Done -> done(event.date)
        }
    }

    class ChooseWeekDialogParams(
        private val calendarMyUseCase: CalendarMyUseCase,
        private val isForMenu: Boolean,
        private val useResult: (LocalDate) -> Unit
    ) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            ChooseWeekViewModel(
                calendarMyUseCase, isForMenu, mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
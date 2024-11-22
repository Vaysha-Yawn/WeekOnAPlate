package week.on.a.plate.screens.wrapperDatePicker.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.repository.tables.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.data.repository.tables.menu.selection.WeekMenuRepository
import week.on.a.plate.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate
import javax.inject.Inject


class WrapperDatePickerManager @Inject constructor(
    private val repository: WeekMenuRepository,
    private val cookRepository: CookPlannerStepRepository
) {

    fun onEvent(event: WrapperDatePickerEvent, wrapperDatePickerUIState: WrapperDatePickerUIState) {
        when (event) {
            is WrapperDatePickerEvent.ChooseWeek -> {} //use chooseWeek fun in vm
            WrapperDatePickerEvent.SwitchWeekOrDayView -> switchWeekOrDayView(wrapperDatePickerUIState)
            WrapperDatePickerEvent.SwitchEditMode -> switchEditMode(wrapperDatePickerUIState)
            is WrapperDatePickerEvent.ChangeWeek -> {} //use changeWeek fun in vm
        }
    }


    fun chooseWeek(
        mainViewModel: MainViewModel,
        wrapperDatePickerUIState: WrapperDatePickerUIState, isForMenu:Boolean, use:(date: LocalDate)->Unit
    ) {
        mainViewModel.viewModelScope.launch {

            val vm = ChooseWeekViewModel(CalendarMyUseCase(repository, cookRepository), mainViewModel)
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
            vm.launchAndGet(isForMenu) { date ->
                changeWeek(date, wrapperDatePickerUIState, use)
            }
        }
    }

    private fun switchWeekOrDayView(wrapperDatePickerUIState: WrapperDatePickerUIState) {
        wrapperDatePickerUIState.itsDayMenu.value = !wrapperDatePickerUIState.itsDayMenu.value
    }

    private fun switchEditMode(wrapperDatePickerUIState: WrapperDatePickerUIState) {
        wrapperDatePickerUIState.isGroupSelectedModeActive.value = !wrapperDatePickerUIState.isGroupSelectedModeActive.value
    }

    fun changeWeek(date: LocalDate, wrapperDatePickerUIState: WrapperDatePickerUIState, use:(date: LocalDate)->Unit) {
        wrapperDatePickerUIState.activeDayInd.value = date.dayOfWeek.ordinal
        use(date)
    }
}
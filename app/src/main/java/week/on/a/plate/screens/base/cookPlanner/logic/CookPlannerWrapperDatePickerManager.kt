package week.on.a.plate.screens.base.cookPlanner.logic

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState

class CookPlannerWrapperDatePickerManager(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val mainViewModel: MainViewModel,
    private val wrapperDatePickerUIState: WrapperDatePickerUIState
) {
    fun onEvent(event: WrapperDatePickerEvent, update: suspend () -> Unit) {
        when (event) {
            is WrapperDatePickerEvent.ChangeWeek -> {
                wrapperDatePickerManager.changeWeek(
                    event.date,
                    wrapperDatePickerUIState
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                    mainViewModel.viewModelScope.launch {
                        update()
                    }
                }
            }

            WrapperDatePickerEvent.ChooseWeek -> {
                wrapperDatePickerManager.chooseWeek(
                    mainViewModel,
                    wrapperDatePickerUIState,
                    false
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                    mainViewModel.viewModelScope.launch {
                        update()
                    }
                }
            }

            WrapperDatePickerEvent.SwitchEditMode -> {}
            WrapperDatePickerEvent.SwitchWeekOrDayView -> {}
        }
    }
}
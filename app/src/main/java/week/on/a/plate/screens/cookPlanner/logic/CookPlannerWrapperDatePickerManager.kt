package week.on.a.plate.screens.cookPlanner.logic

import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.core.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.core.wrapperDatePicker.state.WrapperDatePickerUIState

class CookPlannerWrapperDatePickerManager(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val mainViewModel: MainViewModel,
    private val wrapperDatePickerUIState: WrapperDatePickerUIState
) {
    fun onEvent(event: WrapperDatePickerEvent) {
        when (event) {
            is WrapperDatePickerEvent.ChangeWeek -> {
                wrapperDatePickerManager.changeWeek(
                    event.date,
                    wrapperDatePickerUIState
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                }
            }

            WrapperDatePickerEvent.ChooseWeek -> {
                wrapperDatePickerManager.chooseWeek(
                    mainViewModel,
                    wrapperDatePickerUIState,
                    false
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                }
            }

            WrapperDatePickerEvent.SwitchEditMode -> {}
            WrapperDatePickerEvent.SwitchWeekOrDayView -> {}
        }
    }
}
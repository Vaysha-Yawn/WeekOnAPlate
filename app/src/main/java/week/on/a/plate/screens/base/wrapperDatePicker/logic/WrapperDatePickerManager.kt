package week.on.a.plate.screens.base.wrapperDatePicker.logic

import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.repository.room.cookPlanner.CookPlannerStepRepository
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.dialogs.calendarMy.logic.CalendarMyUseCase
import week.on.a.plate.dialogs.chooseWeekInMenu.logic.ChooseWeekViewModel
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import java.time.LocalDate
import javax.inject.Inject


class WrapperDatePickerManager @Inject constructor(
    private val repository: WeekMenuRepository,
    private val cookRepository: CookPlannerStepRepository
) {

    fun onEvent(
        event: WrapperDatePickerEvent,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
    ) {
        when (event) {
            is WrapperDatePickerEvent.ChooseWeek -> {} //use chooseWeek fun in vm
            WrapperDatePickerEvent.SwitchWeekOrDayView -> switchWeekOrDayView(
                wrapperDatePickerUIState
            )

            WrapperDatePickerEvent.SwitchEditMode -> switchEditMode(wrapperDatePickerUIState)
            is WrapperDatePickerEvent.ChangeWeek -> {} //use changeWeek fun in vm
        }
    }

    fun chooseWeek(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
        isForMenu: Boolean,
        use: (date: LocalDate) -> Unit
    ) {
        val params = ChooseWeekViewModel.ChooseWeekDialogParams(
            CalendarMyUseCase(repository, cookRepository),
            isForMenu
        ) { date ->
            changeWeek(date, wrapperDatePickerUIState, use)
        }
        dialogOpenParams.value = params
    }

    private fun switchWeekOrDayView(wrapperDatePickerUIState: WrapperDatePickerUIState) {
        wrapperDatePickerUIState.itsDayMenu.value = !wrapperDatePickerUIState.itsDayMenu.value
    }

    private fun switchEditMode(wrapperDatePickerUIState: WrapperDatePickerUIState) {
        wrapperDatePickerUIState.isGroupSelectedModeActive.value =
            !wrapperDatePickerUIState.isGroupSelectedModeActive.value
    }

    fun changeWeek(
        date: LocalDate,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
        use: (date: LocalDate) -> Unit
    ) {
        wrapperDatePickerUIState.activeDay.value = date
        use(date)
    }
}
package week.on.a.plate.screens.base.menu.presenter.logic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import javax.inject.Inject

class MenuWrapperDatePickerManager @Inject constructor(private val wrapperDatePickerManager: WrapperDatePickerManager) {
    operator fun invoke(
        event: WrapperDatePickerEvent,
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
        onMenuEvent: (MenuEvent) -> Unit
    ) {
        val activeDay = wrapperDatePickerUIState.activeDay
        when (event) {
            is WrapperDatePickerEvent.ChooseWeek ->
                wrapperDatePickerManager.chooseWeek(
                    mainViewModel,
                    wrapperDatePickerUIState, isForMenu = true
                ) {
                    activeDay.value = it
                    updateWeek()
                }

            WrapperDatePickerEvent.SwitchEditMode -> wrapperDatePickerManager.onEvent(
                event,
                wrapperDatePickerUIState,
            )

            WrapperDatePickerEvent.SwitchWeekOrDayView -> {
                onMenuEvent(MenuEvent.ClearSelected)
                wrapperDatePickerManager.onEvent(
                    event,
                    wrapperDatePickerUIState,
                )
            }

            is WrapperDatePickerEvent.ChangeWeek -> wrapperDatePickerManager.changeWeek(
                event.date,
                wrapperDatePickerUIState
            ) {
                activeDay.value = it
                updateWeek()
            }
        }
    }
}
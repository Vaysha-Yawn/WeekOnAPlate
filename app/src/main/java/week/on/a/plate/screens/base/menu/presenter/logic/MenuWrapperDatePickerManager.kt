package week.on.a.plate.screens.base.menu.presenter.logic

import androidx.compose.runtime.MutableState
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState
import javax.inject.Inject

class MenuWrapperDatePickerManager @Inject constructor(private val wrapperDatePickerManager: WrapperDatePickerManager) {
    operator fun invoke(
        event: WrapperDatePickerEvent,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        updateWeek: () -> Unit,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
        onMenuEvent: (MenuEvent) -> Unit
    ) {
        val activeDay = wrapperDatePickerUIState.activeDay
        when (event) {
            is WrapperDatePickerEvent.ChooseWeek ->
                wrapperDatePickerManager.chooseWeek(
                    dialogOpenParams,
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
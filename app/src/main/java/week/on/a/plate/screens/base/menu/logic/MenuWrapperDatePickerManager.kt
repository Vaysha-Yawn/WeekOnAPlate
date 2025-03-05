package week.on.a.plate.screens.base.menu.logic

import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.SelectedRecipeManager
import week.on.a.plate.screens.base.menu.state.MenuUIState
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
        selectedRecipeManager: SelectedRecipeManager,
        menuUIState: MenuUIState
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
                selectedRecipeManager.clear(menuUIState)
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
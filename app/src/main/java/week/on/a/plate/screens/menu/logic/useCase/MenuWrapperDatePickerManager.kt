package week.on.a.plate.screens.menu.logic.useCase

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.core.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.core.wrapperDatePicker.state.WrapperDatePickerUIState
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.state.MenuUIState
import javax.inject.Inject

class MenuWrapperDatePickerManager @Inject constructor(private val wrapperDatePickerManager: WrapperDatePickerManager) {
    operator fun invoke(
        event: WrapperDatePickerEvent,
        mainViewModel: MainViewModel,
        updateWeek: () -> Unit,
        wrapperDatePickerUIState: WrapperDatePickerUIState,
        selectedRecipeManager:SelectedRecipeManager,
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
                wrapperDatePickerUIState
            )

            WrapperDatePickerEvent.SwitchWeekOrDayView -> {
                selectedRecipeManager.clear(menuUIState)
                wrapperDatePickerManager.onEvent(
                    event,
                    wrapperDatePickerUIState
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
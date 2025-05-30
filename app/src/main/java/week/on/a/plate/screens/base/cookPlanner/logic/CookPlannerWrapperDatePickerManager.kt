package week.on.a.plate.screens.base.cookPlanner.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.base.wrapperDatePicker.logic.WrapperDatePickerManager
import week.on.a.plate.screens.base.wrapperDatePicker.state.WrapperDatePickerUIState

class CookPlannerWrapperDatePickerManager(
    private val wrapperDatePickerManager: WrapperDatePickerManager,
    private val wrapperDatePickerUIState: WrapperDatePickerUIState
) {
    suspend fun onEvent(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        event: WrapperDatePickerEvent,
        scope: CoroutineScope,
        update: suspend () -> Unit
    ) = coroutineScope {
        when (event) {
            is WrapperDatePickerEvent.ChangeWeek -> {
                wrapperDatePickerManager.changeWeek(
                    event.date,
                    wrapperDatePickerUIState
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                    scope.launch(Dispatchers.IO) {
                        update()
                    }
                }
            }

            WrapperDatePickerEvent.ChooseWeek -> {
                wrapperDatePickerManager.chooseWeek(
                    dialogOpenParams,
                    wrapperDatePickerUIState,
                    false
                ) { date ->
                    wrapperDatePickerUIState.activeDay.value = date
                    scope.launch(Dispatchers.IO) {
                        update()
                    }
                }
            }

            WrapperDatePickerEvent.SwitchEditMode -> {}
            WrapperDatePickerEvent.SwitchWeekOrDayView -> {}
        }
    }
}
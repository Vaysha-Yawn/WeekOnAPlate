package week.on.a.plate.screens.additional.specifySelection.logic

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.dialogs.editSelectionDialog.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelectionDialog.state.EditSelectionUIState
import week.on.a.plate.screens.additional.specifySelection.state.SpecifySelectionUIState
import java.time.LocalDateTime
import java.util.Locale
import javax.inject.Inject

class AddCustomSelectionUseCase @Inject constructor(
    private val weekMenuRepository: WeekMenuRepository,
) {
    suspend operator fun invoke(
        dialogOpenParams: MutableState<DialogOpenParams?>,
        state: SpecifySelectionUIState,
        scope: CoroutineScope
    ) {
        val params = EditSelectionViewModel.EditSelectionDialogParams(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            )
        ) { selState ->
            state.allSelectionsIdDay.value =
                state.allSelectionsIdDay.value.toMutableList().apply {
                    add(selState.text.value)
                }
            scope.launch {
                weekMenuRepository.getSelIdOrCreate(
                    LocalDateTime.of(
                        state.date.value,
                        selState.selectedTime.value
                    ),
                    false, selState.text.value, Locale.getDefault(),
                )
            }
            state.checkWeek.value = false
            state.checkDayCategory.value = selState.text.value
        }
        dialogOpenParams.value = params
    }
}
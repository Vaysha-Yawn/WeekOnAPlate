package week.on.a.plate.screens.additional.specifySelection.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.data.repository.room.menu.selection.WeekMenuRepository
import week.on.a.plate.screens.additional.specifySelection.state.SpecifySelectionUIState
import week.on.a.plate.screens.base.menu.dialogs.editSelectionDialog.logic.EditSelectionViewModel
import week.on.a.plate.screens.base.menu.dialogs.editSelectionDialog.state.EditSelectionUIState
import java.time.LocalDateTime
import javax.inject.Inject

class AddCustomSelectionUseCase @Inject constructor(
    private val weekMenuRepository: WeekMenuRepository,
) {
    suspend operator fun invoke(
        mainViewModel: MainViewModel, state: SpecifySelectionUIState, scope: CoroutineScope
    ) {
        EditSelectionViewModel.launch(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            ), mainViewModel
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
                    false, selState.text.value, mainViewModel.locale,
                )
            }
            state.checkWeek.value = false
            state.checkDayCategory.value = selState.text.value
        }
    }
}
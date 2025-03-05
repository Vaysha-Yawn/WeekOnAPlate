package week.on.a.plate.screens.base.menu.logic.usecase

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogs.forMenuScreen.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.forMenuScreen.editSelection.state.EditSelectionUIState
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.base.menu.logic.usecase.navigateLogic.OpenDialogAddPosition
import java.time.LocalDate
import java.time.LocalDateTime

//todo slice
class SelectionUseCase(
    private val mainViewModel: MainViewModel,
    private val viewModelScope: CoroutineScope,
    private val activeDay: MutableState<LocalDate>,
    private val addPosition: OpenDialogAddPosition,
) {
    fun createWeekSelIdAndCreatePosition(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(
                LocalDateTime.of(
                    activeDay.value,
                    ForWeek.stdTime
                ),
                true, context.getString(ForWeek.fullName), mainViewModel.locale,
            )
            addPosition(id, context, mainViewModel)
        }
    }


    fun createSelection(date: LocalDate, isForWeek: Boolean) {
        EditSelectionViewModel.launch(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            ), mainViewModel
        ) { state ->
            viewModelScope.launch(Dispatchers.IO) {
                val newName = state.text.value
                val time = state.selectedTime.value
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.CreateSelection(
                        date,
                        newName, mainViewModel.locale, isForWeek, time
                    )
                )
            }
        }
    }

    fun editOrDeleteSelection(sel: SelectionView) {
        if (sel.isForWeek || sel.id == 0L) return
        EditOrDeleteViewModel.launch(mainViewModel) { event ->
            viewModelScope.launch {
                when (event) {
                    EditOrDeleteEvent.Close -> {}
                    EditOrDeleteEvent.Delete -> deleteSelection(sel)
                    EditOrDeleteEvent.Edit -> editSelection(sel)
                }
            }
        }
    }

    private suspend fun editSelection(sel: SelectionView) = coroutineScope {
        val oldState = EditSelectionUIState(
            mutableStateOf(sel.name), R.string.edit_meal_name,
            R.string.hint_breakfast
        ).apply {
            this.selectedTime.value = sel.dateTime.toLocalTime()
        }
        EditSelectionViewModel.launch(
            oldState, mainViewModel
        ) { state ->
            launch(Dispatchers.IO) {
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.EditSelection(
                        sel,
                        state.text.value, state.selectedTime.value
                    )
                )
            }
        }
    }


    private suspend fun deleteSelection(sel: SelectionView) = coroutineScope {
        sCRUDRecipeInMenu.onEvent(
            ActionWeekMenuDB.DeleteSelection(
                sel
            )
        )
    }


}
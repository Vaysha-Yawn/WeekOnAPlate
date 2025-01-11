package week.on.a.plate.screens.menu.logic.useCase

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.dataView.week.ForWeek
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.dialogs.editOrDelete.event.EditOrDeleteEvent
import week.on.a.plate.dialogs.editOrDelete.logic.EditOrDeleteViewModel
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import java.time.LocalDate
import java.time.LocalDateTime

class SelectionUseCase(
    private val mainViewModel: MainViewModel,
    private val viewModelScope: CoroutineScope,
    private val updateWeek: () -> Unit,
    private val sCRUDRecipeInMenu: CRUDRecipeInMenu,
    private val activeDay: MutableState<LocalDate>,
    private val addPosition: (selId: Long, context: Context) -> Unit
) {

    fun createWeekSelIdAndCreatePosition(context: Context) {
        viewModelScope.launch {
            val id = sCRUDRecipeInMenu.menuR.getSelIdOrCreate(
                LocalDateTime.of(
                    activeDay.value,
                    ForWeek.stdTime
                ),
                true, context.getString(ForWeek.fullName), mainViewModel.locale,
            )
            addPosition(id, context)
        }
    }


    fun createSelection(date: LocalDate, isForWeek: Boolean) {
        EditSelectionViewModel.launch(
            EditSelectionUIState(
                title = R.string.add_meal,
                placeholder = R.string.hint_breakfast
            ), mainViewModel
        ) { state ->
            viewModelScope.launch {
                val newName = state.text.value
                val time = state.selectedTime.value
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.CreateSelection(
                        date,
                        newName, mainViewModel.locale, isForWeek, time
                    )
                )
                updateWeek()
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

    private suspend fun editSelection(sel: SelectionView) {
        val oldState = EditSelectionUIState(
            mutableStateOf(sel.name), R.string.edit_meal_name,
            R.string.hint_breakfast
        ).apply {
            this.selectedTime.value = sel.dateTime.toLocalTime()
        }
        EditSelectionViewModel.launch(
            oldState, mainViewModel
        ) { state ->
            viewModelScope.launch {
                sCRUDRecipeInMenu.onEvent(
                    ActionWeekMenuDB.EditSelection(
                        sel,
                        state.text.value, state.selectedTime.value
                    )
                )
                updateWeek()
            }
        }
    }


    private suspend fun deleteSelection(sel: SelectionView) {
        sCRUDRecipeInMenu.onEvent(
            ActionWeekMenuDB.DeleteSelection(
                sel
            )
        )
        updateWeek()
    }


}
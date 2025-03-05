package week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.logic


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.R
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.forMenuScreen.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.forMenuScreen.editSelection.state.EditSelectionUIState
import week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.event.SetPermanentMealsEvent
import week.on.a.plate.dialogs.forSettingsScreen.setPermanentMeals.state.SetPermanentMealsUIState
import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.app.mainActivity.logic.MainViewModel


class SetPermanentMealsViewModel(
    val dao: CategorySelectionDAO,
    val mainViewModel: MainViewModel,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
) : DialogViewModel<Boolean>(
    scope,
    openDialog,
    closeDialog,
    {}
) {
    val state = SetPermanentMealsUIState()

    fun onEvent(event: SetPermanentMealsEvent) {
        when (event) {
            is SetPermanentMealsEvent.Add -> add()
            SetPermanentMealsEvent.Close -> {
                close()
                //mainViewModel.menuViewModel.updateWeek()
                mainViewModel.specifySelectionViewModel.updateSelections()
            }

            is SetPermanentMealsEvent.Delete -> delete(event.sel)
            is SetPermanentMealsEvent.Edit -> edit(event.sel)
        }
    }

    init {
        viewModelScope.launch {
            state.selections.value = dao.getAll().sortedBy { it.stdTime }
        }
    }

    private fun delete(sel: CategorySelectionRoom) {
        mainViewModel.viewModelScope.launch {
            state.selections.value =
                state.selections.value.toMutableList().apply {
                    remove(sel)
                }

            dao.deleteById(sel.id)
        }
    }

    private fun edit(sel: CategorySelectionRoom) {
        mainViewModel.viewModelScope.launch {
            val stateToEdit = EditSelectionUIState(
                title = R.string.edit_meal,
                placeholder = R.string.hint_breakfast
            ).apply {
                text.value = sel.name
                selectedTime.value = sel.stdTime
            }

            EditSelectionViewModel.launch(stateToEdit, mainViewModel) { selState ->
                mainViewModel.viewModelScope.launch {
                    applyEdit(sel, selState)
                    mainViewModel.onEvent(MainEvent.ShowDialog)
                }
            }
            mainViewModel.onEvent(MainEvent.HideDialog)
        }
    }

    private suspend fun applyEdit(sel: CategorySelectionRoom, selState: EditSelectionUIState) {
        state.selections.value =
            state.selections.value.toMutableList().apply {
                remove(sel)
                add(
                    CategorySelectionRoom(
                        selState.text.value,
                        selState.selectedTime.value
                    )
                )
            }.sortedBy { it.stdTime }

        dao.update(
            CategorySelectionRoom(
                selState.text.value,
                selState.selectedTime.value
            ).apply { id = sel.id })
    }


    private fun add() {
        mainViewModel.viewModelScope.launch {
            EditSelectionViewModel.launch(
                EditSelectionUIState(
                    title = R.string.add_meal,
                    placeholder = R.string.hint_breakfast
                ), mainViewModel
            ) { selState ->
                mainViewModel.viewModelScope.launch {
                    applyAdd(selState)
                }
                mainViewModel.onEvent(MainEvent.ShowDialog)
            }
            mainViewModel.onEvent(MainEvent.HideDialog)
        }
    }

    private suspend fun applyAdd( selState: EditSelectionUIState){
        state.selections.value =
            state.selections.value.toMutableList().apply {
                add(
                    CategorySelectionRoom(
                        selState.text.value,
                        selState.selectedTime.value
                    )
                )
            }.sortedBy { it.stdTime }

        dao.insert(
            CategorySelectionRoom(
                selState.text.value,
                selState.selectedTime.value
            )
        )
    }

    companion object {
        fun launch( dao: CategorySelectionDAO,
            mainViewModel: MainViewModel
        ) {
            SetPermanentMealsViewModel(
                dao,
                mainViewModel,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
            )
        }
    }

}
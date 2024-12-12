package week.on.a.plate.dialogs.setPermanentMeals.logic


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionDAO
import week.on.a.plate.data.repository.tables.menu.category_selection.CategorySelectionRoom
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.dialogs.editSelection.logic.EditSelectionViewModel
import week.on.a.plate.dialogs.editSelection.state.EditSelectionUIState
import week.on.a.plate.dialogs.setPermanentMeals.event.SetPermanentMealsEvent
import week.on.a.plate.dialogs.setPermanentMeals.state.SetPermanentMealsUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import javax.inject.Inject


class SetPermanentMealsViewModel @Inject constructor(val dao: CategorySelectionDAO) :
    DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    val state = SetPermanentMealsUIState()

    fun onEvent(event: SetPermanentMealsEvent) {
        when (event) {
            SetPermanentMealsEvent.Add -> add()
            SetPermanentMealsEvent.Close -> close()
            is SetPermanentMealsEvent.Delete -> delete(event.sel)
            is SetPermanentMealsEvent.Edit -> edit(event.sel)
        }
    }

    fun launch() {
        mainViewModel.viewModelScope.launch {
            state.selections.value = dao.getAll().sortedBy { it.stdTime }
        }
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
        mainViewModel.menuViewModel.updateWeek()
        mainViewModel.specifySelectionViewModel.updateSelections()
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
            val vm = EditSelectionViewModel()
            vm.mainViewModel = mainViewModel
            val stateToEdit = EditSelectionUIState(
                startTitle = "Редактировать приём пищи",
                startPlaceholder = "Завтрак..."
            )
            stateToEdit.text.value = sel.name
            stateToEdit.selectedTime.value = sel.stdTime
            mainViewModel.viewModelScope.launch {
                vm.launchAndGet(
                    stateToEdit
                ) { selState ->
                    mainViewModel.viewModelScope.launch {
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
                        mainViewModel.dialogUseCase.show()
                    }
                }
            }
            mainViewModel.dialogUseCase.hide()
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        }
    }


    private fun add() {
        mainViewModel.viewModelScope.launch {
            val vm = EditSelectionViewModel()
            vm.mainViewModel = mainViewModel
            mainViewModel.viewModelScope.launch {
                vm.launchAndGet(
                    EditSelectionUIState(
                        startTitle = "Добавить приём пищи",
                        startPlaceholder = "Завтрак..."
                    )
                ) { selState ->
                    mainViewModel.viewModelScope.launch {
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
                    mainViewModel.dialogUseCase.show()
                }
            }
            mainViewModel.dialogUseCase.hide()
            mainViewModel.onEvent(MainEvent.OpenDialog(vm))
        }
    }

}
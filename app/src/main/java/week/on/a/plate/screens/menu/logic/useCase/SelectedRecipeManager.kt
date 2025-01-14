package week.on.a.plate.screens.menu.logic.useCase

import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.menu.event.ActionWeekMenuDB
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.event.SelectedEvent
import week.on.a.plate.screens.menu.state.MenuUIState
import javax.inject.Inject

class SelectedRecipeManager @Inject constructor() {

    fun clear(menuUIState: MenuUIState) {
        menuUIState.isAllSelected.value = false
        menuUIState.chosenRecipes = mutableMapOf()
    }

    private fun actionCheckRecipe(menuUIState: MenuUIState, id: Position.PositionRecipeView) {
        val check = menuUIState.chosenRecipes[id] ?: return
        check.value = !check.value
    }

    private fun addNewState(menuUIState: MenuUIState, id: Position.PositionRecipeView) {
        val state = mutableStateOf(false)
        menuUIState.chosenRecipes[id] = state
    }

    fun getSelected(menuUIState: MenuUIState): List<Position.PositionRecipeView> {
        val list = mutableListOf<Position.PositionRecipeView>()
        menuUIState.chosenRecipes.entries.forEach {
            val id = it.key
            val state = it.value
            if (state.value) {
                list.add(id)
            }
        }
        return list
    }

    fun onEvent(selectedEvent: SelectedEvent, menuUIState: MenuUIState) {
        when (selectedEvent) {
            is SelectedEvent.AddCheckState -> addNewState(menuUIState, selectedEvent.recipe)
            is SelectedEvent.CheckRecipe -> actionCheckRecipe(menuUIState, selectedEvent.recipe)
        }
    }


    fun deleteSelected(menuUIState: MenuUIState, onEvent: (MenuEvent) -> Unit) {
        getSelected(menuUIState).forEach {
            onEvent(MenuEvent.ActionDBMenu(ActionWeekMenuDB.Delete(it)))
        }
        onEvent(MenuEvent.ActionWrapperDatePicker(WrapperDatePickerEvent.SwitchEditMode))
    }
}
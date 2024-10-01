package week.on.a.plate.screenMenu.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screenMenu.event.SelectedEvent
import week.on.a.plate.screenMenu.state.MenuIUState
import javax.inject.Inject

class SelectedRecipeManager @Inject constructor() {

    fun clear(menuUIState: MenuIUState) {
        menuUIState.isAllSelected.value = false
        menuUIState.chosenRecipes = mutableMapOf<Position.PositionRecipeView, MutableState<Boolean>>()
    }

    private fun actionCheckRecipe(menuUIState: MenuIUState, id: Position.PositionRecipeView) {
        val check = menuUIState.chosenRecipes[id] ?: return
        check.value = !check.value
    }

    private fun addNewState(menuUIState: MenuIUState, id: Position.PositionRecipeView) {
        val state = mutableStateOf(false)
        menuUIState.chosenRecipes[id] = state
    }

    fun getSelected(menuUIState: MenuIUState): List<Position.PositionRecipeView> {
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

    fun onEvent(selectedEvent: SelectedEvent, menuUIState: MenuIUState) {
        when (selectedEvent) {
            is SelectedEvent.AddCheckState -> addNewState(menuUIState, selectedEvent.recipe)
            is SelectedEvent.CheckRecipe -> actionCheckRecipe(menuUIState, selectedEvent.recipe)
        }
    }

}
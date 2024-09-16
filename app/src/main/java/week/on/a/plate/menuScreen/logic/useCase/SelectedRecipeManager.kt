package week.on.a.plate.menuScreen.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.menuScreen.data.eventData.SelectedData
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
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

    private fun actionChooseAll(menuUIState: MenuIUState) {
        if (menuUIState.isAllSelected.value) {
            menuUIState.chosenRecipes.values.forEach {
                it.value = false
            }
        } else {
            menuUIState.chosenRecipes.values.forEach {
                it.value = true
            }
        }
        menuUIState.isAllSelected.value = !menuUIState.isAllSelected.value
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

    fun onEvent(selectedData: SelectedData, menuUIState: MenuIUState) {
        when (selectedData) {
            is SelectedData.AddCheckState -> addNewState(menuUIState, selectedData.recipe)
            is SelectedData.CheckRecipe -> actionCheckRecipe(menuUIState, selectedData.recipe)
            SelectedData.ChooseAll -> actionChooseAll(menuUIState)
        }
    }

}
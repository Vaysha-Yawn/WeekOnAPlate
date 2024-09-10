package week.on.a.plate.menuScreen.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import week.on.a.plate.menuScreen.logic.eventData.MenuIUState
import javax.inject.Inject

class SelectedRecipeManager @Inject constructor() {

    fun clear(menuUIState: MenuIUState) {
        menuUIState.isAllSelected.value = false
        menuUIState.chosenRecipes = mutableMapOf<Long, MutableState<Boolean>>()
    }

    fun actionCheckRecipe(menuUIState: MenuIUState, id: Long) {
        val check =  menuUIState.chosenRecipes[id] ?: return
        check.value = !check.value
    }

    fun addNewState(menuUIState: MenuIUState, id: Long) {
        val state = mutableStateOf(false)
        menuUIState.chosenRecipes[id] = state
    }

    fun actionChooseAll(menuUIState: MenuIUState) {
        if (menuUIState.isAllSelected.value){
            menuUIState.chosenRecipes.values.forEach {
                it.value = false
            }
        }else{
            menuUIState.chosenRecipes.values.forEach {
                it.value = true
            }
        }
        menuUIState.isAllSelected.value = !menuUIState.isAllSelected.value
    }

    fun getSelected(menuUIState: MenuIUState): List<Long> {
        val list = mutableListOf<Long>()
        menuUIState.chosenRecipes.entries.forEach {
            val id = it.key
            val state = it.value
            if (state.value){list.add(id)}
        }
        return list
    }

}
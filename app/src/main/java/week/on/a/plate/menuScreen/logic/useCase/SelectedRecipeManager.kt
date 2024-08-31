package week.on.a.plate.menuScreen.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import javax.inject.Inject

class SelectedRecipeManager @Inject constructor() {

    var chosenRecipes = mutableMapOf<Long, MutableState<Boolean>>()
    val isAllSelected = mutableStateOf(false)

    fun clear (){
        isAllSelected.value = false
        chosenRecipes = mutableMapOf<Long, MutableState<Boolean>>()
    }

    fun actionCheckRecipe(id: Long) {
        val check = chosenRecipes[id] ?: return
        check.value = !check.value
    }

    fun getState(id: Long) = chosenRecipes[id] ?: addNewState(id)

    private fun addNewState(id: Long): State<Boolean> {
        val state = mutableStateOf(false)
        chosenRecipes[id] = state
        return state
    }

    fun actionChooseAll() {
        if (isAllSelected.value){
            chosenRecipes.values.forEach {
                it.value = false
            }
        }else{
            chosenRecipes.values.forEach {
                it.value = true
            }
        }
        isAllSelected.value = !isAllSelected.value
    }

    fun getSelected(): List<Long> {
        val list = mutableListOf<Long>()
        chosenRecipes.entries.forEach {
            val id = it.key
            val state = it.value
            if (state.value){list.add(id)}
        }
        return list
    }

}
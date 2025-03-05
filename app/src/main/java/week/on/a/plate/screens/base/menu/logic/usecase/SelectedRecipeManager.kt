package week.on.a.plate.screens.base.menu.logic.usecase

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.event.MenuEvent
import week.on.a.plate.screens.base.menu.event.SelectedEvent
import week.on.a.plate.screens.base.menu.logic.usecase.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.state.MenuUIState
import javax.inject.Inject

//todo slice!
class SelectedRecipeManager @Inject constructor(
    private val deleteRecipe: DeleteRecipePosInDBUseCase
) {

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

    suspend fun deleteSelected(menuUIState: MenuUIState, onEvent: (MenuEvent) -> Unit) =
        coroutineScope {
            getSelected(menuUIState).forEach { recipe ->
                launch(Dispatchers.IO) {
                    deleteRecipe(recipe)
                }
        }
        onEvent(MenuEvent.ActionWrapperDatePicker(WrapperDatePickerEvent.SwitchEditMode))
    }
}
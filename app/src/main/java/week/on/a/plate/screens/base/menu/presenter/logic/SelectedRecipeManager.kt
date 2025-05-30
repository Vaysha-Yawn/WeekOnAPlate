package week.on.a.plate.screens.base.menu.presenter.logic

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.base.menu.domain.dbusecase.DeleteRecipePosInDBUseCase
import week.on.a.plate.screens.base.menu.presenter.event.MenuEvent
import week.on.a.plate.screens.base.menu.presenter.event.SelectedEvent
import week.on.a.plate.screens.base.menu.presenter.state.MenuUIState
import week.on.a.plate.screens.base.wrapperDatePicker.event.WrapperDatePickerEvent
import javax.inject.Inject


class SelectedRecipeManager @Inject constructor(
    private val deleteRecipe: DeleteRecipePosInDBUseCase
) {

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

    suspend fun deleteSelected(
        menuUIState: MenuUIState,
        scope: CoroutineScope,
        onEvent: (MenuEvent) -> Unit
    ) =
        coroutineScope {
            getSelected(menuUIState).forEach { recipe ->
                scope.launch(Dispatchers.IO) {
                    deleteRecipe(recipe)
                }
        }
        onEvent(MenuEvent.ActionWrapperDatePicker(WrapperDatePickerEvent.SwitchEditMode))
    }

    private fun actionCheckRecipe(menuUIState: MenuUIState, id: Position.PositionRecipeView) {
        val check = menuUIState.chosenRecipes[id] ?: return
        check.value = !check.value
    }

    private fun addNewState(menuUIState: MenuUIState, id: Position.PositionRecipeView) {
        val state = mutableStateOf(false)
        menuUIState.chosenRecipes[id] = state
    }
}
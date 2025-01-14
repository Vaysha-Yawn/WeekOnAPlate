package week.on.a.plate.screens.createRecipe.logic.useCase

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.createRecipe.state.RecipeStepState

class RecipeCreateStepUseCase(
    val mainViewModel: MainViewModel,
    val state: RecipeCreateUIState
) {
    fun deleteStep(event: RecipeCreateEvent.DeleteStep) {
        state.steps.value = state.steps.value.toMutableList().apply {
            this.remove(event.recipeStepState)
        }.toList()
    }

    fun addStep() {
        mainViewModel.viewModelScope.launch {
            state.steps.value = state.steps.value.toMutableList().apply {
                this.add(RecipeStepState(0))
            }.toList()
        }
    }

    fun editPinnedIngredients(recipeStepState: RecipeStepState) {
        mainViewModel.viewModelScope.launch {
            ChooseIngredientsForStepViewModel.launch(
                mainViewModel,
                state.ingredients.value,
                recipeStepState.pinnedIngredientsInd.value,
            ) { newList ->
                recipeStepState.pinnedIngredientsInd.value = newList
            }
        }
    }
}
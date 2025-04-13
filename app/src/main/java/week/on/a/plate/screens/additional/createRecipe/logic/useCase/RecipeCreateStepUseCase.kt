package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState

class RecipeCreateStepUseCase(
    val mainViewModel: MainViewModel,
    val state: RecipeCreateUIState
) {
    fun deleteStep(event: week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteStep) {
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

    fun editPinnedIngredients(
        recipeStepState: RecipeStepState,
        dialogOpenParams: MutableState<DialogOpenParams?>,
    ) {
        val params = ChooseIngredientsForStepViewModel.ChooseIngredientsForStepDialogParams(
            state.ingredients.value,
            recipeStepState.pinnedIngredientsInd.value,
        ) { newList ->
            recipeStepState.pinnedIngredientsInd.value = newList
        }

        dialogOpenParams.value = params
    }
}
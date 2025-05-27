package week.on.a.plate.screens.additional.createRecipe.logic.useCase

import androidx.compose.runtime.MutableState
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState
import javax.inject.Inject

class RecipeCreateStepUseCase @Inject constructor() {
    fun deleteStep(event: RecipeCreateEvent.DeleteStep, state: RecipeCreateUIState) {
        state.steps.value = state.steps.value.toMutableList().apply {
            this.remove(event.recipeStepState)
        }.toList()
    }

    fun addStep(state: RecipeCreateUIState) {
        state.steps.value = state.steps.value.toMutableList().apply {
            this.add(RecipeStepState(0))
        }.toList()
    }

    fun editPinnedIngredients(
        recipeStepState: RecipeStepState,
        dialogOpenParams: MutableState<DialogOpenParams?>,
        state: RecipeCreateUIState
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
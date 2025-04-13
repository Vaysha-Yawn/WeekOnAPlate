package week.on.a.plate.dialogs.chooseIngredientsForStep.logic

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.core.dialogCore.DialogOpenParams
import week.on.a.plate.core.dialogCore.DialogViewModel
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.state.ChooseIngredientsForStepUIState


class ChooseIngredientsForStepViewModel(
    ingredientsAll: List<IngredientInRecipeView>,
    chosenIngredients: List<Long>,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (List<Long>) -> Unit
) : DialogViewModel<List<Long>>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    var state: ChooseIngredientsForStepUIState =
        ChooseIngredientsForStepUIState(ingredientsAll, mutableStateOf(chosenIngredients))

    fun onEvent(event: ChooseIngredientsForStepEvent) {
        when (event) {
            ChooseIngredientsForStepEvent.Close -> close()
            ChooseIngredientsForStepEvent.Done -> done(state.chosenIngredients.value)
            is ChooseIngredientsForStepEvent.ClickToIngredient -> clickToIngredient(event)
        }
    }

    private fun clickToIngredient(event: ChooseIngredientsForStepEvent.ClickToIngredient) {
        if (state.chosenIngredients.value.contains(event.indIngredient)) {
            state.chosenIngredients.value =
                state.chosenIngredients.value.toMutableList().apply {
                    remove(event.indIngredient)
                }.toList()
        } else {
            state.chosenIngredients.value =
                state.chosenIngredients.value.toMutableList().apply {
                    add(event.indIngredient)
                }.toList()
        }
    }

    class ChooseIngredientsForStepDialogParams(
        private val ingredientsAll: List<IngredientInRecipeView>,
        private val chosenIngredients: List<Long>, private val useResult: (List<Long>) -> Unit
    ) :
        DialogOpenParams {
        override fun openDialog(mainViewModel: MainViewModel) {
            ChooseIngredientsForStepViewModel(
                ingredientsAll, chosenIngredients,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }
}
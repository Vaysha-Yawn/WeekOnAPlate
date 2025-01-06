package week.on.a.plate.dialogs.chooseIngredientsForStep.logic

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.state.ChooseIngredientsForStepUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ChooseIngredientsForStepViewModel(
    ingredientsAll: List<IngredientInRecipeView>,
    chosenIngredients: List<Long>,
    scope: CoroutineScope,
    val closeDialog:()->Unit,
    val use: (List<Long>) -> Unit
) : DialogViewModel() {

    var state: ChooseIngredientsForStepUIState =
        ChooseIngredientsForStepUIState(ingredientsAll, mutableStateOf(chosenIngredients))
    private lateinit var resultFlow: MutableStateFlow<List<Long>?>

    init {
        val flow = start()
        scope.launch {
            flow.collect { value ->
                if (value != null) {
                    use(value)
                }
            }
        }
    }

    fun start(): Flow<List<Long>?> {
        val flow = MutableStateFlow<List<Long>?>(null)
        resultFlow = flow
        return flow
    }

    fun done() {
        close()
        resultFlow.value = state.chosenIngredients.value
    }

    fun close() {
        state.show.value = false
        closeDialog()
    }

    fun onEvent(event: ChooseIngredientsForStepEvent) {
        when (event) {
            ChooseIngredientsForStepEvent.Close -> close()
            ChooseIngredientsForStepEvent.Done -> done()
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

}
package week.on.a.plate.dialogs.chooseIngredientsForStep.logic

import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.state.ChooseIngredientsForStepUIState
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class ChooseIngredientsForStepViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: ChooseIngredientsForStepUIState
    private lateinit var resultFlow: MutableStateFlow<List<Long>?>

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
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun onEvent(event: ChooseIngredientsForStepEvent) {
        when (event) {
            ChooseIngredientsForStepEvent.Close -> close()
            ChooseIngredientsForStepEvent.Done -> done()
            is ChooseIngredientsForStepEvent.ClickToIngredient -> {
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
    }

    suspend fun launchAndGet(
        ingredientsAll: List<IngredientInRecipeView>,
        chosenIngredients: List<Long>,
        use: (List<Long>) -> Unit
    ) {
        state = ChooseIngredientsForStepUIState(ingredientsAll, mutableStateOf(chosenIngredients))
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
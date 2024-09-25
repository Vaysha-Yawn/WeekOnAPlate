package week.on.a.plate.screenFilters.dialogs.filterVoiceApply.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.core.dialogExampleStructure.DialogViewModel
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screenFilters.dialogs.filterVoiceApply.state.FilterVoiceApplyUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class FilterVoiceApplyViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: FilterVoiceApplyUIState
    private lateinit var resultFlow: MutableStateFlow<FilterVoiceApplyUIState?>

    fun start(): Flow<FilterVoiceApplyUIState?> {
        val flow = MutableStateFlow<FilterVoiceApplyUIState?>(null)
        resultFlow = flow
        return flow
    }

    private fun removeSelectedTag(recipeTagView: RecipeTagView) {
        val newList = state.selectedTags.value.toMutableList().apply {
            this.remove(recipeTagView)
        }.toList()
        state.selectedTags.value = newList
    }

    private fun removeSelectedIngredient(ingredientView: IngredientView) {
        val newList = state.selectedIngredients.value.toMutableList().apply {
            this.remove(ingredientView)
        }.toList()
        state.selectedIngredients.value = newList
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
    }

    fun done() {
        close()
        resultFlow.value = state
    }

    fun onEvent(event: FilterVoiceApplyEvent) {
        when (event) {
            FilterVoiceApplyEvent.Close -> close()
            FilterVoiceApplyEvent.Done -> done()
            is FilterVoiceApplyEvent.RemoveSelectedIngredient -> removeSelectedIngredient(event.ingredientView)
            is FilterVoiceApplyEvent.RemoveSelectedTag -> removeSelectedTag(event.recipeTagView)
        }
    }

    suspend fun launchAndGet(selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>, use: (FilterVoiceApplyUIState) -> Unit) {
        state = FilterVoiceApplyUIState(selectedTags, selectedIngredients)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
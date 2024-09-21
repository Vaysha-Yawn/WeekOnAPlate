package week.on.a.plate.core.dialogs.selectedFilters.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.dialogAbstract.event.DialogEvent
import week.on.a.plate.core.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.core.dialogs.selectedFilters.state.SelectedFiltersUIState
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel


class SelectedFiltersViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: SelectedFiltersUIState
    private lateinit var resultFlow: MutableStateFlow<SelectedFiltersUIState?>

    fun start(): Flow<SelectedFiltersUIState?> {
        val flow = MutableStateFlow<SelectedFiltersUIState?>(null)
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
        resultFlow.value = state
    }

    fun onEvent(event: SelectedFiltersEvent) {
        when (event) {
            SelectedFiltersEvent.Close -> close()
            is SelectedFiltersEvent.RemoveSelectedIngredient -> removeSelectedIngredient(event.ingredientView)
            is SelectedFiltersEvent.RemoveSelectedTag -> removeSelectedTag(event.recipeTagView)
        }
    }

    //when close use compare state.selectedIngredients.value and state.selectedTags.value
    suspend fun launchAndGet(selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>, use: (SelectedFiltersUIState) -> Unit) {
        state = SelectedFiltersUIState(selectedTags, selectedIngredients)
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
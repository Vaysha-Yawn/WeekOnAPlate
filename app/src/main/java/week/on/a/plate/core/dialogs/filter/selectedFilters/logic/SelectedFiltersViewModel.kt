package week.on.a.plate.core.dialogs.filter.selectedFilters.logic

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.dialogs.DialogViewModel
import week.on.a.plate.core.dialogs.filter.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.core.dialogs.filter.selectedFilters.state.SelectedFiltersUIState
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.MainViewModel


class SelectedFiltersViewModel() : DialogViewModel() {

    lateinit var mainViewModel: MainViewModel
    lateinit var state: SelectedFiltersUIState
    private lateinit var resultFlow: MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>

    fun start(): Flow<Pair<List<RecipeTagView>, List<IngredientView>>?> {
        val flow = MutableStateFlow<Pair<List<RecipeTagView>, List<IngredientView>>?>(null)
        resultFlow = flow
        return flow
    }

    private fun removeSelectedTag(recipeTagView: RecipeTagView) {
        val newList = state.selectedTags.value.toMutableList().apply {
            this.remove(recipeTagView)
        }.toList()
        state.selectedTags.value = newList
        resultFlow.value = Pair(state.selectedTags.value, state.selectedIngredients.value)
    }

    private fun removeSelectedIngredient(ingredientView: IngredientView) {
        val newList = state.selectedIngredients.value.toMutableList().apply {
            this.remove(ingredientView)
        }.toList()
        state.selectedIngredients.value = newList
        resultFlow.value = Pair(state.selectedTags.value, state.selectedIngredients.value)
    }

    fun close() {
        state.show.value = false
        mainViewModel.onEvent(MainEvent.CloseDialog)
        resultFlow.value = Pair(state.selectedTags.value, state.selectedIngredients.value)
    }

    fun onEvent(event: SelectedFiltersEvent) {
        when (event) {
            SelectedFiltersEvent.Close -> close()
            is SelectedFiltersEvent.RemoveSelectedIngredient -> removeSelectedIngredient(event.ingredientView)
            is SelectedFiltersEvent.RemoveSelectedTag -> removeSelectedTag(event.recipeTagView)
        }
    }

    suspend fun launchAndGet(startIndicator:Int,  selectedTags: List<RecipeTagView>, selectedIngredients: List<IngredientView>, use: (Pair<List<RecipeTagView>, List<IngredientView>>) -> Unit) {
        state = SelectedFiltersUIState(selectedTags, selectedIngredients)
        state.activeFilterTabIndex.intValue = startIndicator
        val flow = start()
        flow.collect { value ->
            if (value != null) {
                use(value)
            }
        }
    }

}
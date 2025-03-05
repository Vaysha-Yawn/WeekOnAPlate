package week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.logic

import kotlinx.coroutines.CoroutineScope
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.screens.additional.filters.dialogs.selectedFilters.state.SelectedFiltersUIState


class SelectedFiltersViewModel(
    startIndicator: Int,
    selectedTags: List<RecipeTagView>,
    selectedIngredients: List<IngredientView>,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (Pair<List<RecipeTagView>, List<IngredientView>>) -> Unit
) : DialogViewModel<Pair<List<RecipeTagView>, List<IngredientView>>>(
    scope,
    openDialog,
    closeDialog,
    use
) {

    val state: SelectedFiltersUIState =
        SelectedFiltersUIState(selectedTags, selectedIngredients).apply {
            activeFilterTabIndex.intValue = startIndicator
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

    fun onEvent(event: SelectedFiltersEvent) {
        when (event) {
            SelectedFiltersEvent.Close -> done(
                Pair(
                    state.selectedTags.value,
                    state.selectedIngredients.value
                )
            )

            is SelectedFiltersEvent.RemoveSelectedIngredient -> removeSelectedIngredient(event.ingredientView)
            is SelectedFiltersEvent.RemoveSelectedTag -> removeSelectedTag(event.recipeTagView)
        }
    }

    companion object {
        fun launch(
            startIndicator: Int,
            selectedTags: List<RecipeTagView>,
            selectedIngredients: List<IngredientView>, mainViewModel: MainViewModel,
            useResult: (Pair<List<RecipeTagView>, List<IngredientView>>) -> Unit,
        ) {
            SelectedFiltersViewModel(
                startIndicator,
                selectedTags,
                selectedIngredients,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
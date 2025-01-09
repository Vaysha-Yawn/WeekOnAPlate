package week.on.a.plate.screens.filters.dialogs.filterVoiceApply.logic

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.dialogs.addPositionChoose.event.AddPositionEvent
import week.on.a.plate.dialogs.addPositionChoose.logic.AddPositionViewModel
import week.on.a.plate.dialogs.core.DialogViewModel
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.state.FilterVoiceApplyUIState
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel


class FilterVoiceApplyViewModel(
    selectedTags: List<RecipeTagView>,
    selectedIngredients: List<IngredientView>,
    scope: CoroutineScope,
    openDialog: (DialogViewModel<*>) -> Unit,
    closeDialog: () -> Unit,
    use: (FilterVoiceApplyUIState) -> Unit
) : DialogViewModel<FilterVoiceApplyUIState>(
    scope,
    openDialog,
    closeDialog,
    use
) {
    val state: FilterVoiceApplyUIState = FilterVoiceApplyUIState(selectedTags, selectedIngredients)

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

    fun onEvent(event: FilterVoiceApplyEvent) {
        when (event) {
            FilterVoiceApplyEvent.Close -> close()
            FilterVoiceApplyEvent.Done -> done(state)
            is FilterVoiceApplyEvent.RemoveSelectedIngredient -> removeSelectedIngredient(event.ingredientView)
            is FilterVoiceApplyEvent.RemoveSelectedTag -> removeSelectedTag(event.recipeTagView)
        }
    }

    companion object {
        fun launch(selectedTags: List<RecipeTagView>,
                   selectedIngredients: List<IngredientView>,
            mainViewModel: MainViewModel, useResult: (FilterVoiceApplyUIState) -> Unit
        ) {
            FilterVoiceApplyViewModel(selectedTags, selectedIngredients,
                mainViewModel.getCoroutineScope(),
                mainViewModel::openDialog,
                mainViewModel::closeDialog,
                useResult
            )
        }
    }

}
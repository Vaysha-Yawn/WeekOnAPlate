package week.on.a.plate.screens.searchRecipes.logic

import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.searchRecipes.state.SearchState
import week.on.a.plate.screens.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SearchStateManager @Inject constructor() {
    fun close(state: SearchUIState, mainViewModel:MainViewModel) {
        if (state.resultSearch.value.isNotEmpty() || state.selectedTags.value.isNotEmpty() ||
            state.selectedIngredients.value.isNotEmpty() ||
            state.searchText.value != "" || state.searched.value == SearchState.done
        ) {
            state.resultSearch.value = listOf()
            state.selectedTags.value = listOf()
            state.selectedIngredients.value = listOf()
            state.searchText.value = ""
            state.searched.value = SearchState.none
            state.favoriteChecked.value = false
            state.allTime.intValue = 0
            state.prepTime.intValue = 0
        } else {
            mainViewModel.onEvent(MainEvent.NavigateBack)
        }
    }

    fun searchClear(state: SearchUIState,) {
        state.searchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
        state.searched.value = SearchState.none
    }
}
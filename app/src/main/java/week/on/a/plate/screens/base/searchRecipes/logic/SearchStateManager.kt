package week.on.a.plate.screens.base.searchRecipes.logic

import week.on.a.plate.app.mainActivity.event.MainEvent
import week.on.a.plate.screens.base.searchRecipes.state.SearchState
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import javax.inject.Inject

class SearchStateManager @Inject constructor() {
    fun close(state: SearchUIState, onEvent: (MainEvent) -> Unit) {
        if (state.resultSearch.value.isNotEmpty() || state.selectedTags.value.isNotEmpty() ||
            state.selectedIngredients.value.isNotEmpty() ||
            state.searchText.value != "" || state.searched.value == SearchState.Done
        ) {
            state.resultSearch.value = listOf()
            state.selectedTags.value = listOf()
            state.selectedIngredients.value = listOf()
            state.searchText.value = ""
            state.searched.value = SearchState.None
            state.favoriteChecked.value = false
            state.allTime.intValue = 0
            state.prepTime.intValue = 0
        } else {
            onEvent(MainEvent.NavigateBack)
        }
    }

    fun searchClear(state: SearchUIState,) {
        state.searchText.value = ""
        state.selectedTags.value = listOf()
        state.selectedIngredients.value = listOf()
        state.searched.value = SearchState.None
    }
}
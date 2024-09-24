package week.on.a.plate.search.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainViewModel
import week.on.a.plate.search.logic.SearchViewModel
import week.on.a.plate.search.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.search.view.categoriesScreen.TopSearchPanel
import week.on.a.plate.search.view.resultScreen.SearchResultScreen
import week.on.a.plate.search.view.searchInWeb.SearchInWeb

@Composable
fun SearchStart(
    mainViewModel: MainViewModel,
) {
    val viewModel = mainViewModel.searchViewModel
    val onEvent = { eventData: Event ->
        mainViewModel.searchViewModel.onEvent(eventData)
    }
    if (viewModel.state.isSearchInWeb.value){
        SearchInWeb(viewModel.state.searchText.value){
            viewModel.state.isSearchInWeb.value = false
            //onEventSaveNewRecipe
        }
    }else {
        Column {
            TopSearchPanel(viewModel.state, onEvent)
            if (viewModel.state.resultSearch.value.isNotEmpty()) {
                SearchResultScreen(viewModel.state.resultSearch.value, onEvent)
            } else if (viewModel.state.searchText.value != "" || viewModel.state.selectedTags.value.isNotEmpty() || viewModel.state.selectedIngredients.value.isNotEmpty()) {
                SearchNothingFound(viewModel.state, onEvent)
            } else {
                SearchCategoriesScreen(viewModel.state, onEvent)
            }
        }
    }
}
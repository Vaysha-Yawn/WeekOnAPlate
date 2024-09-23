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

@Composable
fun SearchStart(
    mainViewModel: MainViewModel,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    viewModel.mainViewModel = mainViewModel

    val onEvent = { eventData: Event ->
        viewModel.onEvent(eventData)
    }

    Column {
        TopSearchPanel(viewModel.state, onEvent)
        if (viewModel.state.resultSearch.value.isNotEmpty()) {
            SearchResultScreen(viewModel.state.resultSearch.value, onEvent)
        } else {
            SearchCategoriesScreen(viewModel.state, onEvent)
        }
    }
}
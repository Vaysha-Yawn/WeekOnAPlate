package week.on.a.plate.search.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainViewModel
import week.on.a.plate.search.logic.SearchViewModel
import week.on.a.plate.search.navigation.SearchRoute
import week.on.a.plate.search.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.search.view.categoriesScreen.TopSearchPanel
import week.on.a.plate.search.view.resultScreen.SearchResultScreen

@Composable
fun SearchStart(
    arguments: SearchRoute,
    mainViewModel: MainViewModel,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    viewModel.mainViewModel = mainViewModel

    val onEvent = { eventData: Event ->
        viewModel.onEvent(eventData)
    }
    //  purpose
    when(arguments){
        SearchRoute.SearchDestination -> {}
        is SearchRoute.SearchWithSelId -> {}
    }

    Column {
        TopSearchPanel(viewModel.stateUI, onEvent)
        if (viewModel.stateUI.resultSearch.value.isNotEmpty()) {
            SearchResultScreen(viewModel.stateUI.resultSearch.value, onEvent)
        } else {
            SearchCategoriesScreen(viewModel.stateUI, onEvent)
        }
    }
}
package week.on.a.plate.search.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.logic.SearchViewModel
import week.on.a.plate.search.navigation.Search
import week.on.a.plate.search.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.search.view.categoriesScreen.TopSearchPanel
import week.on.a.plate.search.view.filtersScreen.FilterScreen
import week.on.a.plate.search.view.filtersScreen.TopSearchPanelFilter
import week.on.a.plate.search.view.resultScreen.SearchResultScreen

@Composable
fun SearchStart(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
    arguments: Search,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    viewModel.snackbarHostState = snackbarHostState
    viewModel.navController = navController
    viewModel.bottomBarState = bottomBarState

    val onEvent = { eventData: SearchScreenEvent ->
        viewModel.onEvent(eventData)
    }
    Column {
        if (viewModel.stateUI.showFilter.value) {
            TopSearchPanelFilter(stateUI = viewModel.stateUI, onEvent)
            FilterScreen(viewModel.stateUI, onEvent)
        } else {
            TopSearchPanel(viewModel.stateUI, onEvent)
            Spacer(modifier = Modifier.height(24.dp))
            if (viewModel.stateUI.resultSearch.value.isNotEmpty()) {
                SearchResultScreen(viewModel.stateUI, onEvent)
            } else {
                SearchCategoriesScreen(viewModel.stateUI, onEvent)
            }
        }
    }
}


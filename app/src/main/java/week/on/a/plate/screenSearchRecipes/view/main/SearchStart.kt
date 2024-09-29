package week.on.a.plate.screenSearchRecipes.view.main

import android.view.SearchEvent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent
import week.on.a.plate.screenSearchRecipes.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.screenSearchRecipes.view.categoriesScreen.TopSearchPanel
import week.on.a.plate.screenSearchRecipes.view.resultScreen.SearchResultScreen

@Composable
fun SearchStart(
    mainViewModel: MainViewModel,
) {
    val viewModel = mainViewModel.searchViewModel
    val onEvent = { eventData: Event ->
        mainViewModel.searchViewModel.onEvent(eventData)
    }
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
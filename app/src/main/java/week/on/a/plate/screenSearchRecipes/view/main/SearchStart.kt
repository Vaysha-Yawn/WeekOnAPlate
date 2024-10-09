package week.on.a.plate.screenSearchRecipes.view.main


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenSearchRecipes.state.SearchState
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

    viewModel.state.allTagsCategories = viewModel.allTagCategories.collectAsState()
    Column {
        TopSearchPanel(viewModel.state, onEvent)
        if (viewModel.state.resultSearch.value.isNotEmpty() &&
            (viewModel.state.searchText.value != ""
                    || viewModel.state.selectedTags.value.isNotEmpty()
                    || viewModel.state.selectedIngredients.value.isNotEmpty()
                    || viewModel.state.searched.value == SearchState.done)
        ) {
            SearchResultScreen(viewModel.state.resultSearch.value,viewModel.state, onEvent)
        } else if (viewModel.state.searchText.value != ""
            || viewModel.state.selectedTags.value.isNotEmpty()
            || viewModel.state.selectedIngredients.value.isNotEmpty()
            || viewModel.state.searched.value == SearchState.done
        ) {
            SearchNothingFound(viewModel.state, onEvent)
        } else {
            SearchCategoriesScreen(viewModel.state, onEvent)
        }
    }
}
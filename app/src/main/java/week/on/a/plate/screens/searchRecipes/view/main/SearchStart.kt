package week.on.a.plate.screens.searchRecipes.view.main


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screens.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.searchRecipes.state.SearchState
import week.on.a.plate.screens.searchRecipes.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.screens.searchRecipes.view.resultScreen.SearchResultScreen

@Composable
fun SearchStart(
    mainViewModel: MainViewModel,
) {
    val viewModel = mainViewModel.searchViewModel
    val state = viewModel.state
    val onEvent = { eventData: Event ->
        mainViewModel.searchViewModel.onEvent(eventData)
    }

    state.allTagsCategories = viewModel.allTagCategories.collectAsState()
    Column {
        SearchResultEditRow(state, onEvent)
        Spacer(modifier = Modifier.size(6.dp))
        TopSearchPanel(state, onEvent)
        Spacer(modifier = Modifier.size(6.dp))
        if (state.resultSearch.value.isNotEmpty() &&
            (state.searchText.value != ""
                    || state.selectedTags.value.isNotEmpty()
                    || state.selectedIngredients.value.isNotEmpty()
                    || state.searched.value == SearchState.done)
        ) {
            SearchResultScreen(state.resultSearch.value, state, onEvent)
            BackHandler {
                onEvent(SearchScreenEvent.Back)
            }
        } else if (state.searchText.value != ""
            || state.selectedTags.value.isNotEmpty()
            || state.selectedIngredients.value.isNotEmpty()
            || state.searched.value == SearchState.done
        ) {
            SearchNothingFound(state, onEvent)
            BackHandler {
                onEvent(SearchScreenEvent.Back)
            }
        } else {
            SearchCategoriesScreen(state, onEvent)
        }
    }
}
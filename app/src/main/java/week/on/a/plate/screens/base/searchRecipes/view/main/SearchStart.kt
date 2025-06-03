package week.on.a.plate.screens.base.searchRecipes.view.main


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.logic.SearchViewModel
import week.on.a.plate.screens.base.searchRecipes.state.SearchState
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState
import week.on.a.plate.screens.base.searchRecipes.view.categoriesScreen.SearchCategoriesScreen
import week.on.a.plate.screens.base.searchRecipes.view.resultScreen.SearchResultScreen

@Composable
fun SearchStart(
    viewModel: SearchViewModel,
    viewModel1: MainViewModel,
) {
    val state = viewModel.state
    val onEvent = { eventData: Event ->
        viewModel.onEvent(eventData)
    }
    state.allTagsCategories = viewModel.allTagCategories.collectAsState()
    SearchStartContent(state, onEvent)
    MainEventResolve(viewModel.mainEvent, viewModel.dialogOpenParams, viewModel1)
}

@Composable
private fun SearchStartContent(
    state: SearchUIState, onEvent: (Event) -> Unit
) {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()) {
        SearchResultEditRow(state, onEvent)
        Spacer(modifier = Modifier.size(6.dp))
        TopSearchPanel(state, onEvent)
        Spacer(modifier = Modifier.size(6.dp))
        if (state.resultSearch.value.isNotEmpty() &&
            (state.searchText.value != ""
                    || state.selectedTags.value.isNotEmpty()
                    || state.selectedIngredients.value.isNotEmpty()
                    || state.searched.value == SearchState.Done)
        ) {
            SearchResultScreen(state.resultSearch.value, state, onEvent)
            BackHandler {
                onEvent(SearchScreenEvent.Back)
            }
        } else if (state.searchText.value != ""
            || state.selectedTags.value.isNotEmpty()
            || state.selectedIngredients.value.isNotEmpty()
            || state.searched.value == SearchState.Done
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

@Preview(showBackground = true)
@Composable
fun PreviewSearch() {
    WeekOnAPlateTheme {
        SearchStartContent(SearchUIState()) {}
    }
}
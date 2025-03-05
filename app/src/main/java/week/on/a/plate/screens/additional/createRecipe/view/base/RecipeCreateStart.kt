package week.on.a.plate.screens.additional.createRecipe.view.base

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.additional.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.view.recipe.RecipeEditPage
import week.on.a.plate.screens.additional.createRecipe.view.recipe.TopBarRecipeCreate
import week.on.a.plate.screens.additional.createRecipe.view.web.RowWebActions
import week.on.a.plate.screens.additional.createRecipe.view.web.WebPageCreateRecipe

@Composable
fun RecipeCreateStart(viewModel: RecipeCreateViewModel) {
    val onEvent = { event: Event ->
        viewModel.onEvent(event)
    }
    val state = rememberLazyListState()

    RecipeCreateBackHandler(viewModel.state, onEvent)

    RecipeCreateStartContent(viewModel.state, onEvent, state)
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun RecipeCreateStartContent(
    state: RecipeCreateUIState,
    onEvent: (Event) -> Unit,
    stateList: LazyListState
) {
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        TopBarRecipeCreate(state, onEvent)
        LazyColumn(
            state = stateList,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(24.dp))
                NameRecipeEdit(state)
                Spacer(modifier = Modifier.height(24.dp))
                SourceRecipeEdit(state)
            }
            stickyHeader {
                TabCreateRecipe(state)
                if (state.activeTabIndex.intValue == 1) {
                    RowWebActions(state)
                }
            }
            if (state.activeTabIndex.intValue == 1) {
                item {
                    WebPageCreateRecipe(state) { event -> onEvent(event) }
                }
            } else {
                RecipeEditPage(state, onEvent)
            }
        }
    }
}


@Composable
private fun RecipeCreateBackHandler(state: RecipeCreateUIState, onEvent: (Event) -> Unit) {
    BackHandler {
        if (state.activeTabIndex.intValue == 0) {
            onEvent(week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.OpenDialogExitApplyFromCreateRecipe)
        } else {
            if (state.webview.value?.canGoBack() == true) {
                state.webview.value!!.goBack()
            } else {
                onEvent(week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.OpenDialogExitApplyFromCreateRecipe)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewRecipeCreateStart() {
    WeekOnAPlateTheme {
        RecipeCreateStartContent(RecipeCreateUIState(), {}, rememberLazyListState())
    }
}
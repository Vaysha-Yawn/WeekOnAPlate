package week.on.a.plate.screens.base.searchRecipes.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.buttons.DoneButtonSmall
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState

@Composable
fun SearchNothingFound(state: SearchUIState, onEvent: (Event) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)) {
        TextTitleLarge(text = stringResource(R.string.no_result), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(24.dp))
        DoneButtonSmall(text = stringResource(R.string.create_recipe) + state.searchText.value) {
            onEvent(SearchScreenEvent.CreateRecipe)
        }
    }
}
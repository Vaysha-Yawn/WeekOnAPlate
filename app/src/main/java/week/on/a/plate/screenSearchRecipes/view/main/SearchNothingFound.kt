package week.on.a.plate.screenSearchRecipes.view.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.buttons.DoneButtonSmall
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent
import week.on.a.plate.screenSearchRecipes.state.SearchUIState

@Composable
fun SearchNothingFound(state: SearchUIState, onEvent: (Event) -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp)) {
        TextTitleLarge(text = "По этому запросу ничего не найдено", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Start)
        Spacer(modifier = Modifier.height(24.dp))
        DoneButtonSmall(text = "Создать рецепт: ${state.searchText.value}") {
            onEvent(SearchScreenEvent.CreateRecipe)
        }
    }
}
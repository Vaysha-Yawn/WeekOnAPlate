package week.on.a.plate.search.view.categoriesScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.MainEvent
import week.on.a.plate.core.fullScereenDialog.filters.navigation.FilterDestination
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.search.event.SearchScreenEvent
import week.on.a.plate.search.state.SearchUIState
import week.on.a.plate.search.view.viewTools.FilterButton

@Composable
fun TopSearchPanel(stateUI: SearchUIState, onEvent: (Event) -> Unit) {
    Row(Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
        BackButtonOutlined {
            onEvent(SearchScreenEvent.Back)
        }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = stateUI.searchText,
            modifier = Modifier.weight(1f),
            actionSearch = { s -> onEvent(SearchScreenEvent.Search(s)) },
            actionSearchVoice = {
                onEvent(
                    SearchScreenEvent.VoiceSearch
                )
            })
        Spacer(modifier = Modifier.width(12.dp))
        FilterButton(stateUI.selectedTags.value.size + stateUI.selectedIngredients.value.size) { onEvent(SearchScreenEvent.ToFilter)}
    }
}


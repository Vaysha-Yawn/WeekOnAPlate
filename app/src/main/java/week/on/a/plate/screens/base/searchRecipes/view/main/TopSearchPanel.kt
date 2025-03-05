package week.on.a.plate.screens.base.searchRecipes.view.main

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState

@Composable
fun TopSearchPanel(stateUI: SearchUIState, onEvent: (Event) -> Unit) {
    Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
        if (stateUI.resultSearch.value.isNotEmpty()) {
            BackButtonOutlined {
                onEvent(SearchScreenEvent.Back)
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
        val context = LocalContext.current
        SearchLine(
            textSearch = stateUI.searchText,
            modifier = Modifier.weight(1f),
            actionSearch = { s -> onEvent(SearchScreenEvent.Search) },
            actionSearchVoice = {
                onEvent(
                    SearchScreenEvent.VoiceSearch(context)
                )
            }, {onEvent(SearchScreenEvent.Clear)})
    }
}


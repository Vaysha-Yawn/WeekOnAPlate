package week.on.a.plate.search.view.filtersScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
import week.on.a.plate.search.view.viewTools.SearchLine

@Composable
fun TopSearchPanelFilter(stateUI: SearchUIState, onEvent: (SearchScreenEvent) -> Unit) {
    Row() {
        BackButtonOutlined { onEvent(SearchScreenEvent.CloseFilters) }
        SearchLine(
            textSearch = stateUI.filtersSearchText,
            actionSearch = { s -> onEvent(SearchScreenEvent.SearchFilters(s)) },
            actionSearchVoice = { onEvent(SearchScreenEvent.VoiceSearchFilters) })
    }
}
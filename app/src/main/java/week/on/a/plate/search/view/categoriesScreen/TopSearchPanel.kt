package week.on.a.plate.search.view.categoriesScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
import week.on.a.plate.search.view.viewTools.FilterButton
import week.on.a.plate.search.view.viewTools.SearchLine

@Composable
fun TopSearchPanel(stateUI: SearchUIState, onEvent: (SearchScreenEvent) -> Unit) {
    Row() {
        BackButtonOutlined {
            if (stateUI.resultSearch.value.isNotEmpty()) {
                onEvent(SearchScreenEvent.ClearResultSearch)
            } else {
                onEvent(SearchScreenEvent.NavigateBack)
            }
            }
        SearchLine(textSearch = stateUI.searchText, actionSearch = { s-> onEvent(SearchScreenEvent.Search(s))}, actionSearchVoice = {onEvent(SearchScreenEvent.VoiceSearch)})
        FilterButton { onEvent(SearchScreenEvent.OpenFilter) }
    }
}


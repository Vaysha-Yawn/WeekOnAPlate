package week.on.a.plate.search.view.categoriesScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.filters.navigation.FilterRoute
import week.on.a.plate.search.data.SearchScreenEvent
import week.on.a.plate.search.data.SearchUIState
import week.on.a.plate.search.view.viewTools.FilterButton
import week.on.a.plate.search.view.viewTools.SearchLine

@Composable
fun TopSearchPanel(stateUI: SearchUIState, onEvent: (Event) -> Unit) {
    Row(Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
        BackButtonOutlined {
            if (stateUI.resultSearch.value.isNotEmpty()) {
                onEvent(SearchScreenEvent.ClearResultSearch)
            } else {
                onEvent(MainEvent.NavigateBack)
            }
            }

        SearchLine(textSearch = stateUI.searchText, actionSearch = { s-> onEvent(SearchScreenEvent.Search(s))}, actionSearchVoice = {onEvent(SearchScreenEvent.VoiceSearch)})
        FilterButton { onEvent(MainEvent.Navigate(FilterRoute.FilterDestination)) }
    }
}


package week.on.a.plate.search.view.filtersScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.filters.FilterEvent
import week.on.a.plate.filters.FilterUIState
import week.on.a.plate.search.view.viewTools.SearchLine
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun TopSearchPanelFilter(stateUI: FilterUIState, onEvent: (Event) -> Unit) {
    Row(Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
        BackButtonOutlined { onEvent(MainEvent.NavigateBack) }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = stateUI.filtersSearchText,
            actionSearch = { s -> onEvent(FilterEvent.SearchFilter(s, stateUI.activeFilterTabIndex.intValue==1)) },
            actionSearchVoice = { onEvent(FilterEvent.VoiceSearchFilters) })
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTopSearchPanelFilter() {
    WeekOnAPlateTheme {
        TopSearchPanelFilter(FilterUIState()) {}
    }
}
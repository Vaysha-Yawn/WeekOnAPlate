package week.on.a.plate.core.fullScereenDialog.filters.view

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.buttons.BackButtonOutlined
import week.on.a.plate.core.fullScereenDialog.filters.event.FilterEvent
import week.on.a.plate.core.fullScereenDialog.filters.state.FilterUIState
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun TopSearchPanelFilter(stateUI: FilterUIState, onEvent: (FilterEvent)->Unit) {
    Row(Modifier.background(MaterialTheme.colorScheme.background).padding(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        BackButtonOutlined { onEvent(FilterEvent.Back) }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = stateUI.filtersSearchText,
            actionSearch = { s -> onEvent(FilterEvent.SearchFilter(s)) },
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
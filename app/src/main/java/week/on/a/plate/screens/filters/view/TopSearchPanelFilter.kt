package week.on.a.plate.screens.filters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.screens.filters.event.FilterEvent
import week.on.a.plate.screens.filters.state.FilterMode
import week.on.a.plate.screens.filters.state.FilterUIState

@Composable
fun TopSearchPanelFilter(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 6.dp, horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painterResource(R.drawable.back), "", modifier = Modifier
                .clickable { onEvent(FilterEvent.Done) }
                .padding(6.dp)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (stateUI.filterMode.value == FilterMode.Multiple) {
                    Icon(painterResource(R.drawable.close), "", modifier = Modifier
                        .clickable { onEvent(FilterEvent.Back) }
                        .padding(6.dp)
                    )
                    Spacer(Modifier.width(24.dp))
                    Icon(painterResource(R.drawable.select_all), "", modifier = Modifier
                        .clickable { onEvent(FilterEvent.SelectedFilters) }
                        .padding(6.dp)
                    )
                }
            }
        }

        HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.outline)

        Row(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 24.dp, horizontal = 12.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            val modifier = if (stateUI.filterMode.value == FilterMode.One) {
                val focusRequester = remember { FocusRequester() }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                Modifier.focusRequester(focusRequester)
            } else {
                Modifier
            }

            SearchLine(
                textSearch = stateUI.searchText,
                modifier = modifier.weight(1f),
                actionSearch = { s -> onEvent(FilterEvent.SearchFilter(s)) },
                actionSearchVoice = { onEvent(FilterEvent.VoiceSearchFilters) },
                actionClear = {}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTopSearchPanelFilter() {
    WeekOnAPlateTheme {
        TopSearchPanelFilter(FilterUIState()) {}
    }
}
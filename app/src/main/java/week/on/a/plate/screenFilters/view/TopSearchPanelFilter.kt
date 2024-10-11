package week.on.a.plate.screenFilters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorStrokeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterMode
import week.on.a.plate.screenFilters.state.FilterUIState
import week.on.a.plate.screenRecipeDetails.view.start.ImageButton

@Composable
fun TopSearchPanelFilter(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 24.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(12.dp))
        ImageButton(
            res = R.drawable.close,
            Modifier
                .border(1.dp, ColorStrokeGrey, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
        ) { onEvent(FilterEvent.Back) }

        Spacer(modifier = Modifier.width(12.dp))

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
        Spacer(modifier = Modifier.width(12.dp))
        if (stateUI.filterMode.value == FilterMode.Multiple) {
            ImageButton(
                res = R.drawable.select_all,
                Modifier
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
                    .border(1.dp, ColorStrokeGrey, RoundedCornerShape(10.dp))
            ) { onEvent(FilterEvent.SelectedFilters) }
            Spacer(modifier = Modifier.width(12.dp))
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
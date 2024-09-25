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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorPanelGreen
import week.on.a.plate.screenFilters.event.FilterEvent
import week.on.a.plate.screenFilters.state.FilterUIState
import week.on.a.plate.core.uitools.SearchLine
import week.on.a.plate.screenRecipeDetails.view.start.ImageButton
import week.on.a.plate.core.theme.ColorStrokeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun TopSearchPanelFilter(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    Row(
        Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 24.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        if(stateUI.selectedTags.value.isNotEmpty() || stateUI.selectedIngredients.value.isNotEmpty()){
            Spacer(modifier = Modifier.width(12.dp))
            ImageButton(
                res = R.drawable.close,
                Modifier
                    .border(1.dp, ColorStrokeGrey, RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(10.dp))
            ) { onEvent(FilterEvent.ClearSearch) }
        }
        Spacer(modifier = Modifier.width(12.dp))
        SearchLine(
            textSearch = stateUI.filtersSearchText,
            modifier = Modifier.weight(1f),
            actionSearch = { s -> onEvent(FilterEvent.SearchFilter(s)) },
            actionSearchVoice = { onEvent(FilterEvent.VoiceSearchFilters) },
            actionClear = {}
        )
        Spacer(modifier = Modifier.width(12.dp))
        ImageButton(
            res = R.drawable.check,
            Modifier
                .border(1.dp, MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(10.dp))
        ) {  onEvent(FilterEvent.Back) }
        Spacer(modifier = Modifier.width(12.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTopSearchPanelFilter() {
    WeekOnAPlateTheme {
        TopSearchPanelFilter(FilterUIState()) {}
    }
}
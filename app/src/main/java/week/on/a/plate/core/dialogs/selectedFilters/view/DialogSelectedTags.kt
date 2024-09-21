package week.on.a.plate.core.dialogs.selectedFilters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.dialogs.selectedFilters.event.SelectedFiltersEvent
import week.on.a.plate.core.dialogs.selectedFilters.state.SelectedFiltersUIState
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.search.view.filtersScreen.TabRowFilter
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun DialogSelectedTags(
    stateUI: SelectedFiltersUIState,
    event: (SelectedFiltersEvent) -> Unit
) {
    Column() {
        TextTitleItalic(
            text = stringResource(R.string.selected_positions),
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )
        TabRowFilter(
            stateUI.activeFilterTabIndex,
            stateUI.selectedTags.value.size,
            stateUI.selectedIngredients.value.size
        )
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(12.dp)
        ) {
            when (stateUI.activeFilterTabIndex.intValue) {
                0 -> {
                    items(stateUI.selectedTags.value.size) { ind ->
                        TagSelected(stateUI.selectedTags.value[ind]) {
                            event(SelectedFiltersEvent.RemoveSelectedTag(stateUI.selectedTags.value[ind]))
                        }
                    }
                }

                1 -> {
                    items(stateUI.selectedIngredients.value.size) {
                        IngredientSelected(stateUI.selectedIngredients.value[it]) {
                            event(SelectedFiltersEvent.RemoveSelectedIngredient(stateUI.selectedIngredients.value[it]))
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDialogSelectedTags() {
    WeekOnAPlateTheme {
        DialogSelectedTags(SelectedFiltersUIState(listOf(), listOf())) { }
    }
}

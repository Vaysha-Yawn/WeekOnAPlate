package week.on.a.plate.screens.filters.dialogs.filterVoiceApply.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.screens.filters.dialogs.filterVoiceApply.state.FilterVoiceApplyUIState
import week.on.a.plate.screens.filters.dialogs.selectedFilters.view.IngredientSelected
import week.on.a.plate.screens.filters.dialogs.selectedFilters.view.TagSelected
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun DialogVoiceApplyTags(
    state: FilterVoiceApplyUIState,
    event: (FilterVoiceApplyEvent) -> Unit
) {
    Column(Modifier.background(MaterialTheme.colorScheme.surface)) {
        TextTitleItalic(
            text = stringResource(R.string.add_positions_voice_apply),
            Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            textAlign = TextAlign.Center
        )
        LazyColumn(Modifier.padding(horizontal = 24.dp)) {
            items(state.selectedTags.value.size) {
                TagSelected(state.selectedTags.value[it]) {
                    event(FilterVoiceApplyEvent.RemoveSelectedTag(state.selectedTags.value[it]))
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
            items(state.selectedIngredients.value.size) {
                IngredientSelected(state.selectedIngredients.value[it]) {
                    event(
                        FilterVoiceApplyEvent.RemoveSelectedIngredient(
                            state.selectedIngredients.value[it]
                        )
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
        DoneButton(
            text = stringResource(R.string.apply),
            Modifier
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            event(FilterVoiceApplyEvent.Done)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewDialogVoiceApply() {
    WeekOnAPlateTheme {
        DialogVoiceApplyTags(FilterVoiceApplyUIState(listOf(), listOf())) {}
    }
}

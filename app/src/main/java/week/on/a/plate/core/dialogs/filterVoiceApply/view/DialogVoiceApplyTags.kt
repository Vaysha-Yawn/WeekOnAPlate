package week.on.a.plate.core.dialogs.filterVoiceApply.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.ingredients
import week.on.a.plate.core.data.example.tags
import week.on.a.plate.core.data.recipe.IngredientView
import week.on.a.plate.core.data.recipe.RecipeTagView
import week.on.a.plate.core.dialogs.filterVoiceApply.event.FilterVoiceApplyEvent
import week.on.a.plate.core.dialogs.filterVoiceApply.state.FilterVoiceApplyUIState
import week.on.a.plate.core.dialogs.selectedFilters.view.IngredientSelected
import week.on.a.plate.core.dialogs.selectedFilters.view.TagSelected
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.filters.FilterEvent
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun DialogVoiceApplyTags(
    state: FilterVoiceApplyUIState,
    event: (FilterVoiceApplyEvent) -> Unit
) {
    Scaffold(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface), floatingActionButton = {
        DoneButton(
            text = stringResource(R.string.apply),
            Modifier
                .padding(horizontal = 20.dp)
                .offset(x = 15.dp)
        ) {
            event(FilterVoiceApplyEvent.Done)
        }
    }) { innerPadding ->
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
        }
        innerPadding
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialogVoiceApply() {
    WeekOnAPlateTheme {
        DialogVoiceApplyTags(FilterVoiceApplyUIState(listOf(), listOf())) {}
    }
}

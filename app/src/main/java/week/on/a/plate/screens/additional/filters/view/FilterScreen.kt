package week.on.a.plate.screens.additional.filters.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.data.dataView.example.getStartIngredients
import week.on.a.plate.data.dataView.example.getTags
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.screens.additional.filters.event.FilterEvent
import week.on.a.plate.screens.additional.filters.state.FilterEnum
import week.on.a.plate.screens.additional.filters.state.FilterMode
import week.on.a.plate.screens.additional.filters.state.FilterUIState


@Composable
fun FilterScreen(stateUI: FilterUIState, onEvent: (FilterEvent) -> Unit) {
    val context = LocalContext.current
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.surface)
            .imePadding()
    ) {
        if (stateUI.filterEnum.value == FilterEnum.IngredientAndTag) {
            TabRowFilter(
                stateUI.activeFilterTabIndex,
                stateUI.selectedTags.value.size,
                stateUI.selectedIngredients.value.size
            )
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 24.dp)
        ) {

            createFilterIfEmpty(stateUI, onEvent, context)
            tag(stateUI, onEvent, context)
            ingredient(stateUI, onEvent, context)
            categoryTag(stateUI, onEvent, context)
            categoryIngredient(stateUI, onEvent, context)

            item {
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
        if (stateUI.filterMode.value == FilterMode.Multiple) {
            DoneButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 12.dp)
                    .padding(top = 6.dp),
                text = stringResource(R.string.apply),
            ) {
                onEvent(FilterEvent.Done)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFilterScreen() {
    WeekOnAPlateTheme {
        val tags = getTags(LocalContext.current)
        FilterScreen(FilterUIState().apply {
            searchText.value = "По"
            activeFilterTabIndex.intValue = 1
            selectedTags.value = selectedTags.value.toMutableList().apply {
                tags.map { it -> it.tags }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
            val ingredients = getStartIngredients(LocalContext.current)
            selectedIngredients.value = selectedIngredients.value.toMutableList().apply {
                ingredients.map { it -> it.ingredientViews }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }

            resultSearchTags.value = mutableListOf<RecipeTagView>().toMutableList().apply {
                tags.map { it -> it.tags }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
            resultSearchIngredients.value = mutableListOf<IngredientView>().apply {
                ingredients.map { it -> it.ingredientViews }.forEach { list ->
                    this.addAll(list)
                }
                this.removeAt(1)
                this.removeAt(2)
            }
        }) {

        }
    }
}

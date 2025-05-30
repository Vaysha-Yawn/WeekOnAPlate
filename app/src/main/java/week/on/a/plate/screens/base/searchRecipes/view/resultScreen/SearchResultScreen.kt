package week.on.a.plate.screens.base.searchRecipes.view.resultScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent
import week.on.a.plate.screens.base.searchRecipes.state.SearchUIState

@Composable
fun SearchResultScreen(
    result: List<RecipeView>,
    state: SearchUIState,
    onEvent: (SearchScreenEvent) -> Unit
) {
    if (result.isEmpty()) return
    LazyColumn() {
        itemsIndexed(result, key = { _, it -> it.id }) { index, recipe ->
            if (state.modeResultViewIsList.value) {
                RowRecipeResultList(recipe, onEvent)
            } else {
                if (index % 2 == 0) {
                    Row(Modifier.padding(horizontal = 12.dp)) {
                        CardRecipeResult(
                            recipe,
                            onEvent,
                            Modifier
                                .fillMaxWidth(0.5f)
                                .padding(end = 6.dp)
                        )
                        if (result.size > index + 1) {
                            CardRecipeResult(
                                result[index + 1], onEvent,
                                Modifier
                                    .fillMaxWidth(1f)
                                    .padding(start = 6.dp)
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewSearchResult() {
    WeekOnAPlateTheme {
        Column(Modifier.fillMaxWidth()) {
            SearchResultScreen(
                listOf(recipeTom, recipeTom, recipeTom, recipeTom),
                SearchUIState()
            ) {}
        }
    }
}
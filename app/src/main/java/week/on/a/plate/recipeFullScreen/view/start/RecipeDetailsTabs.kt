package week.on.a.plate.recipeFullScreen.view.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.recipeTom
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun RecipeDetailsTabs(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.value == null) return
    val tabTitles = listOf("По шагам", "Ингредиенты", "Источник")
    TabRow( modifier = Modifier.fillMaxWidth(),
        selectedTabIndex = state.activeTabIndex.intValue,
        indicator = { tabPositions ->
            TabRowDefaults.SecondaryIndicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[state.activeTabIndex.intValue]),
                color = MaterialTheme.colorScheme.onBackground,
                height = 2.dp
            )
        }) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                text = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        TextSmall(
                            text =
                            when (index) {
                                0 -> state.recipe.value!!.allTime.toString() + " мин"
                                1 -> state.recipe.value!!.ingredients.size.toString()
                                2 -> ""
                                else -> ""
                            }
                        )
                        TextSmall(text = title, )
                    }
                },
                selected = state.activeTabIndex.intValue == index,
                onClick = { state.activeTabIndex.intValue = index }
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsTabs() {
    WeekOnAPlateTheme {
        RecipeDetailsTabs(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }) {}
    }
}
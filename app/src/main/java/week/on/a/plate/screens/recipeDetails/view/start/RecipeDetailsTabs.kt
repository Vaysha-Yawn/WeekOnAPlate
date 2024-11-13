package week.on.a.plate.screens.recipeDetails.view.start

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.screens.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.utils.timeToString

@Composable
fun RecipeDetailsTabs(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    val tabTitles = listOf("По шагам", "Состав", "Источник")
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
                                0 -> state.recipe.value.steps.maxOf { it.start+it.duration }.toInt().timeToString()
                                1 -> state.recipe.value.ingredients.size.toString()
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
            recipe = mutableStateOf(recipeTom)
        }) {}
    }
}
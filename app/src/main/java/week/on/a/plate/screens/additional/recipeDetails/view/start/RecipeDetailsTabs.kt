package week.on.a.plate.screens.additional.recipeDetails.view.start

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.utils.timeToString

@Composable
fun RecipeDetailsTabs(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    val tabTitles = listOf(stringResource(R.string.step_by_step),
        stringResource(R.string.recipe_composition), stringResource(R.string.source)
    )
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
                                0 -> state.recipe.duration.toSecondOfDay().timeToString(LocalContext.current)
                                1 -> state.recipe.ingredients.size.toString()
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
            recipe = recipeTom
        }) {}
    }
}
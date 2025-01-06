package week.on.a.plate.screens.createRecipe.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun SourceRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    Column(Modifier.padding(horizontal =  24.dp)) {
        TextBody(text = stringResource(R.string.source_title))
        Spacer(modifier = Modifier.height(12.dp))
        EditTextLine(text = state.source, placeholder = stringResource(R.string.enter_recipe_link))
    }
}

@Composable
fun TabCreateRecipe(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit){
    val tabTitles = listOf(stringResource(R.string.recipe), stringResource(R.string.source))
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
                        TextSmall(text = title)
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
fun PreviewSourceRecipeEdit() {
    WeekOnAPlateTheme {
        Column(Modifier.padding(24.dp)) {
            SourceRecipeEdit(RecipeCreateUIState()) {}
        }
    }
}
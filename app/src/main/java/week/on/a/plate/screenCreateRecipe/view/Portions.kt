package week.on.a.plate.screenCreateRecipe.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.state.RecipeCreateUIState

@Composable
fun PortionsRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    TextTitle(text = "Колличество порций", Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.height(12.dp))
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        ButtonsCounterSmall(
            value = state.portionsCount,
            minus = {
                if (state.portionsCount.intValue>0) {

                    state.portionsCount.intValue = state.portionsCount.intValue.minus(1)
                }
            }) {
            state.portionsCount.intValue = state.portionsCount.intValue.plus(1)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeEdit() {
    WeekOnAPlateTheme {
        PortionsRecipeEdit(RecipeCreateUIState()) {}
    }
}
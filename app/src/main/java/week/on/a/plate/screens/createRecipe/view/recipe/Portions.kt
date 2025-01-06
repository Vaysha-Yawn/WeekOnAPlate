package week.on.a.plate.screens.createRecipe.view.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun PortionsRecipeEdit(
    modifier: Modifier,
    state: RecipeCreateUIState,
    onEvent: (RecipeCreateEvent) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        TextBody(
            text = stringResource(R.string.portions_count), Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(24.dp))
        ButtonsCounterSmall(
            value = state.portionsCount,
            minus = {
                if (state.portionsCount.intValue > 0) {

                    state.portionsCount.intValue = state.portionsCount.intValue.minus(1)
                }
            }) {
            state.portionsCount.intValue = state.portionsCount.intValue.plus(1)
        }
    }
}
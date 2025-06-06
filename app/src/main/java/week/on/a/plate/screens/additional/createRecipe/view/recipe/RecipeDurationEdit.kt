package week.on.a.plate.screens.additional.createRecipe.view.recipe

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState

@Composable
fun RecipeDurationEdit(
    modifier: Modifier,
    state: RecipeCreateUIState,
    onEvent: (RecipeCreateEvent) -> Unit
) {
    val context = LocalContext.current
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        TextBody(
            text = stringResource(R.string.recipe_duration), Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(24.dp))
        TimerButton(state.duration.value.toSecondOfDay()) {
            onEvent(RecipeCreateEvent.EditRecipeDuration(context), )
        }
    }
}
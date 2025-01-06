package week.on.a.plate.screens.createRecipe.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun NameRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    Column(Modifier.padding(horizontal =  24.dp)) {
        TextBody(text = stringResource(R.string.recipe_name))
        Spacer(modifier = Modifier.height(12.dp))
        EditTextLine(text = state.name, placeholder = stringResource(R.string.enter_recipe_name))
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewNameRecipeEdit() {
    WeekOnAPlateTheme {
        NameRecipeEdit(RecipeCreateUIState()) {}
    }
}
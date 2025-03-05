package week.on.a.plate.screens.additional.createRecipe.view.recipe

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState

@Composable
fun DescriptionRecipeEdit(state: RecipeCreateUIState) {
    TextTitle(text = stringResource(R.string.recipe_description))
    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(text = state.description, placeholder = stringResource(R.string.enter_recipe_description))
}

@Preview(showBackground = true)
@Composable
fun PreviewDescriptionRecipeEdit() {
    WeekOnAPlateTheme {
        DescriptionRecipeEdit(RecipeCreateUIState())
    }
}
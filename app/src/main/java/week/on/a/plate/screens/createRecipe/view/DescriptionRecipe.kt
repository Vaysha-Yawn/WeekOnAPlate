package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState

@Composable
fun DescriptionRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    TextBody(text = "Описание рецепта")
    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(text = state.description, placeholder = "Введите описание рецепта")
}

@Preview(showBackground = true)
@Composable
fun PreviewDescriptionRecipeEdit() {
    WeekOnAPlateTheme {
        DescriptionRecipeEdit(RecipeCreateUIState()) {}
    }
}
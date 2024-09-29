package week.on.a.plate.screenCreateRecipe.view

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.state.RecipeCreateUIState

@Composable
fun NameRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    TextTitle(text = "Название рецепта")
    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(text = state.name, placeholder = "Введите название рецепта") {
        state.name.value = it
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNameRecipeEdit() {
    WeekOnAPlateTheme {
        NameRecipeEdit(RecipeCreateUIState()) {}
    }
}
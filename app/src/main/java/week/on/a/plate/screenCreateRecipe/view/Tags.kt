package week.on.a.plate.screenCreateRecipe.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.state.RecipeCreateUIState
import week.on.a.plate.screenSearchRecipes.view.resultScreen.TagList

@Composable
fun TagsRecipeEdit(state: RecipeCreateUIState, onEvent: (RecipeCreateEvent) -> Unit) {
    TextTitle(text = "Тэги")
    Spacer(modifier = Modifier.height(12.dp))
    TagList(state.tags.value, listOf())
    Spacer(modifier = Modifier.height(12.dp))
    CommonButton(text = "Редактировать тэги") {
        onEvent(RecipeCreateEvent.EditTags)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTagsRecipeEdit() {
    WeekOnAPlateTheme {
        Column(Modifier.padding(24.dp)) {
            TagsRecipeEdit(RecipeCreateUIState()) {}
        }
    }
}
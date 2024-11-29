package week.on.a.plate.screens.recipeDetails.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.dateToStringShort
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState
import week.on.a.plate.screens.searchRecipes.view.resultScreen.TagList
import java.time.format.DateTimeFormatter

@Composable
fun RecipeBase(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.img != "") {
        ImageLoad(
            state.recipe.img,
            Modifier
                .height(200.dp)
                .fillMaxWidth()
                .clipToBounds()
                .scale(1.2f)
        )
    }
    Column(
        Modifier
            .padding(horizontal = 36.dp)
            .padding(top = 24.dp)
    ) {
        TextTitleLarge(text = state.recipe.name)
        if (state.recipe.tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            TagList(tags = state.recipe.tags, ingredients = listOf())
        }
        Spacer(modifier = Modifier.height(12.dp))
        SubText(
            text = "Последнее редактирование: " + state.recipe.lastEdit.toLocalDate()
                .dateToStringShort() + " в " + state.recipe.lastEdit.format(
                DateTimeFormatter.ofPattern("HH:mm")
            ), textAlign = TextAlign.Start
        )
        if (state.recipe.description != "") {
            Spacer(modifier = Modifier.height(12.dp))
            TextBody(text = state.recipe.description)
        }
    }
    Spacer(modifier = Modifier.height(36.dp))
    DoneButton(
        "Добавить рецепт в меню",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 36.dp)
    )
    { onEvent(RecipeDetailsEvent.AddToMenu) }
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeBase() {
    WeekOnAPlateTheme {
        Column(Modifier.fillMaxWidth()) {
            RecipeBase(RecipeDetailsState().apply {
                this.recipe = recipeTom
            }) {}
        }
    }
}
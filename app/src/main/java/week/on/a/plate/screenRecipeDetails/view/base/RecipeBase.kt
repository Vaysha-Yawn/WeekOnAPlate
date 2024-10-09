package week.on.a.plate.screenRecipeDetails.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.screenSearchRecipes.view.resultScreen.TagList

@Composable
fun RecipeBase(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.value.img.startsWith("http")) {
        ImageLoad(
            state.recipe.value.img,
            Modifier
                .height(200.dp)
                .fillMaxWidth().clipToBounds().scale(1.2f)
        )
    }
    Column(
        Modifier
            .padding(horizontal = 36.dp)
            .padding(top = 24.dp)) {
        TextTitleLarge(text = state.recipe.value.name)
        if (state.recipe.value.tags.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            TagList(tags = state.recipe.value.tags, ingredients = listOf())
        }
        if (state.recipe.value.description!=""){
            Spacer(modifier = Modifier.height(24.dp))
            TextBody(text = state.recipe.value.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeBase() {
    WeekOnAPlateTheme {
        RecipeBase(RecipeDetailsState().apply {
            this.recipe = mutableStateOf(recipeTom)
        }) {}
    }
}
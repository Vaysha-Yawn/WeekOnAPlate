package week.on.a.plate.recipeFullScreen.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.core.data.example.recipeTom
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.search.view.resultScreen.TagList
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun RecipeBase(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (state.recipe.value==null) return
    Column(Modifier.padding(24.dp)) {
       if (state.recipe.value?.img?.startsWith("http") == true) {
            AsyncImage(model = state.recipe.value!!.img, "", modifier = Modifier.fillMaxWidth())
       }
        Spacer(modifier = Modifier.height(12.dp))
        TextTitle(text = state.recipe.value!!.name)
        Spacer(modifier = Modifier.height(12.dp))
        TagList(tags = state.recipe.value!!.tags, ingredients = listOf())
        Spacer(modifier = Modifier.height(24.dp))
        TextBody(text = state.recipe.value!!.description)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeBase() {
    WeekOnAPlateTheme {
        RecipeBase(RecipeDetailsState().apply {
            this.recipe.value = recipeTom
        }) {}
    }
}
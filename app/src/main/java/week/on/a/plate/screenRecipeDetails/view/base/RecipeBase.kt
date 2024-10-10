package week.on.a.plate.screenRecipeDetails.view.base

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextDisplayItalic
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.TextTitleLarge
import week.on.a.plate.core.utils.dateToString
import week.on.a.plate.core.utils.dateToStringShort
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.screenSearchRecipes.view.resultScreen.TagList
import java.time.format.DateTimeFormatter

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
        Spacer(modifier = Modifier.height(12.dp))
        SubText(text = "Создано: " + state.recipe.value.dateCreated.dateToStringShort(),)
        Spacer(modifier = Modifier.height(6.dp))
        SubText(text = "Последнее редактирование: " + state.recipe.value.dateLastEdit.dateToStringShort() + " в "+ state.recipe.value.timeLastEdit.format(
            DateTimeFormatter.ofPattern("HH:mm")))
        if (state.recipe.value.description!=""){
            Spacer(modifier = Modifier.height(12.dp))
            TextBody(text = state.recipe.value.description)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeBase() {
    WeekOnAPlateTheme {
        Column(Modifier.fillMaxWidth()) {
            RecipeBase(RecipeDetailsState().apply {
                this.recipe = mutableStateOf(recipeTom)
            }) {}
        }
    }
}
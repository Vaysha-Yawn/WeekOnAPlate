package week.on.a.plate.screenCreateRecipe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screenCreateRecipe.event.RecipeCreateEvent
import week.on.a.plate.screenCreateRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screenCreateRecipe.state.RecipeCreateUIState
import week.on.a.plate.screenCreateRecipe.state.RecipeStepState

@Composable
fun StepRecipeEdit(
    index: Int,
    recipeStepState: RecipeStepState,
    state: RecipeCreateUIState,
    onEvent: (RecipeCreateEvent) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        onEvent(RecipeCreateEvent.DeleteStep(recipeStepState))
                    }
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextTitleItalic(text = "${index + 1} шаг")
            Spacer(modifier = Modifier.width(6.dp))
        }
        if (recipeStepState.image.value == "") {
            Row(
                Modifier
                    .clickable {
                        onEvent(RecipeCreateEvent.EditImage(recipeStepState))
                    }
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.photo), contentDescription = "")
                Spacer(modifier = Modifier.width(5.dp))
                TextBody(text = "Фото")
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
        if (recipeStepState.timer.intValue == 0) {
            Row(
                Modifier
                    .clickable {
                        onEvent(RecipeCreateEvent.EditTimer(recipeStepState))
                    }
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(id = R.drawable.timer), contentDescription = "")
                Spacer(modifier = Modifier.width(5.dp))
                TextBody(text = "Таймер")
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(text = recipeStepState.description, placeholder = "Введите описание шага")
    if (recipeStepState.image.value != "") {
        Spacer(modifier = Modifier.height(24.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onEvent(RecipeCreateEvent.DeleteImage(recipeStepState))
                })
            Spacer(modifier = Modifier.width(12.dp))
            ImageLoad(url = recipeStepState.image.value, modifier = Modifier
                .height(160.dp)
                .clickable {
                    onEvent(RecipeCreateEvent.EditImage(recipeStepState))
                })
        }
    }
    if (recipeStepState.timer.intValue != 0) {
        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onEvent(RecipeCreateEvent.ClearTime(recipeStepState))
                })
            Spacer(modifier = Modifier.width(12.dp))
            TimerButton(recipeStepState.timer.intValue) {
                onEvent(RecipeCreateEvent.EditTimer(recipeStepState))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStepRecipeEdit() {
    WeekOnAPlateTheme {
        val vm = RecipeCreateViewModel()
        vm.setStateByOldRecipe(recipeTom)
        Column {
            StepRecipeEdit(0, vm.state.steps.value[0], vm.state) {}
            StepRecipeEdit(1, vm.state.steps.value[1], vm.state) {}
        }
    }
}
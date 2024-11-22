package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.ImageLoadEditable
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.createRecipe.event.RecipeCreateEvent
import week.on.a.plate.screens.createRecipe.logic.RecipeCreateViewModel
import week.on.a.plate.screens.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.createRecipe.state.RecipeStepState


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
            ImageStepButton(recipeStepState, onEvent)
        }
        if (recipeStepState.timer.longValue == 0L) {
            TimerStep(recipeStepState, onEvent)
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
    EditTextLine(text = recipeStepState.description, placeholder = "Введите описание шага")
    if (recipeStepState.image.value != "") {
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onEvent(RecipeCreateEvent.DeleteImage(recipeStepState))
                })
            Spacer(modifier = Modifier.width(12.dp))
            val context = LocalContext.current
            ImageLoadEditable(url = recipeStepState.image.value,
                recipeStepState.imageContainer,
                modifier = Modifier
                    .height(160.dp)
                    .clickable {
                        onEvent(RecipeCreateEvent.EditImage(recipeStepState, context))
                    })
        }
    }

    if (recipeStepState.timer.longValue != 0L) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "",
                modifier = Modifier.clickable {
                    onEvent(RecipeCreateEvent.ClearTime(recipeStepState))
                })
            Spacer(modifier = Modifier.width(12.dp))
            TimerButton(recipeStepState.timer.longValue.toInt()) {
                onEvent(RecipeCreateEvent.EditTimer(recipeStepState))
            }
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
    DurationStep(recipeStepState, onEvent)
    if (recipeStepState.pinnedIngredientsInd.value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(12.dp))
        PinnedIngredientsForStep(
            recipeStepState.pinnedIngredientsInd.value.map { id ->
                state.ingredients.value.find { it.ingredientView.ingredientId == id }!!
            }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    CommonButton("Редактировать прикреплённые ингредиенты") {
        onEvent(RecipeCreateEvent.EditPinnedIngredients(recipeStepState))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DurationStep(state: RecipeStepState, onEvent: (RecipeCreateEvent) -> Unit) {
    val suggest = listOf(300, 600, 900, 1200, 1800,)
    val cur =state.duration.toInt().timeToString()
    TextBody(text = "Текущая  длительность шага: ${if (cur=="") "0 мин" else cur}", Modifier)
    Spacer(Modifier.height(12.dp))
    TextBody(text = "Изменить длительность шага:", Modifier)
    Spacer(Modifier.height(12.dp))
    LazyRow(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        stickyHeader {
            Row(
                Modifier
                    .clickable {
                        onEvent(RecipeCreateEvent.SetCustomDuration(state))
                    }
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
                    .padding(horizontal = 12.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextBody(text = "Другое")
            }
            Spacer(Modifier.width(12.dp))
        }
        itemsIndexed(suggest) { ind, item ->
            TimeButton(item) {
                onEvent(RecipeCreateEvent.EditStepDuration(state, item))
            }
            Spacer(Modifier.width(12.dp))
        }
    }
}

@Composable
fun TimeButton(time: Int, edit: () -> Unit) {
    Row(
        Modifier
            .clickable {
                edit()
            }
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextBody(text = time.timeToString())
    }
}

@Composable
fun ImageStepButton(recipeStepState: RecipeStepState, onEvent: (RecipeCreateEvent) -> Unit) {
    val context = LocalContext.current
    Row(
        Modifier
            .clickable {
                onEvent(RecipeCreateEvent.EditImage(recipeStepState, context))
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

@Composable
fun TimerStep(recipeStepState: RecipeStepState, onEvent: (RecipeCreateEvent) -> Unit) {
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

@Preview(showBackground = true)
@Composable
fun PreviewStepRecipeEdit() {
    WeekOnAPlateTheme {
        val vm = RecipeCreateViewModel()
        vm.setStateByOldRecipe(recipeTom)
        Column {
            PinnedIngredientsForStep(vm.state.steps.value[0].pinnedIngredientsInd.value.map { id ->
                vm.state.ingredients.value.find { it.ingredientView.ingredientId == id }!!
            })
            StepRecipeEdit(0, vm.state.steps.value[0], vm.state) {}
            StepRecipeEdit(1, vm.state.steps.value[1], vm.state) {}
        }
    }
}
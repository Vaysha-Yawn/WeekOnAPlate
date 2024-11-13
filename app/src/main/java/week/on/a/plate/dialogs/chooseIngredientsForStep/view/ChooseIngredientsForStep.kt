package week.on.a.plate.dialogs.chooseIngredientsForStep.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.example.recipes
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.logic.ChooseIngredientsForStepViewModel
import week.on.a.plate.dialogs.selectNStep.event.SelectNStepEvent
import week.on.a.plate.dialogs.selectNStep.logic.SelectNStepViewModel
import week.on.a.plate.screens.recipeDetails.view.ingredients.IngredientInRecipeCard

@Composable
fun ChooseIngredientsForStep(viewModel: ChooseIngredientsForStepViewModel) {
    val state = viewModel.state
    val onEvent = { event: ChooseIngredientsForStepEvent ->
        viewModel.onEvent(event)
    }
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier =  Modifier.padding(bottom = 12.dp)) {
            TextTitle(
                text = "Выберите ингредиенты", textAlign = TextAlign.End)
        }

        LazyColumn() {
            itemsIndexed(state.ingredientsAll){ind, item->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .clickable {
                            onEvent(ChooseIngredientsForStepEvent.ClickToIngredient(item.ingredientView.ingredientId))
                        }
                        .padding( vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Checkbox(
                        checked = state.chosenIngredients.value.contains(item.ingredientView.ingredientId),
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.secondary,
                            checkmarkColor = MaterialTheme.colorScheme.onBackground
                        ),
                        onCheckedChange = {
                            onEvent(ChooseIngredientsForStepEvent.ClickToIngredient(item.ingredientView.ingredientId))
                        },
                    )
                    if (item.ingredientView.img!="") {
                        ImageLoad(
                            url = item.ingredientView.img, modifier = Modifier
                                .height(40.dp)
                                .clip(RoundedCornerShape(10.dp))
                        )
                        Spacer(modifier = Modifier.width(24.dp))
                    }
                    Column(Modifier.weight(1f)) {
                        TextBody(text = item.ingredientView.name, color = MaterialTheme.colorScheme.onBackground)
                        if (item.description != "") {
                            TextBodyDisActive(text = item.description)
                        }
                    }
                    Row {
                        val valueAndMeasure = getIngredientCountAndMeasure1000(item.count, item.ingredientView.measure)
                        TextBody(
                            text = valueAndMeasure.first, color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        TextBody(text = valueAndMeasure.second, color = MaterialTheme.colorScheme.onBackground)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.done)) {
            onEvent(ChooseIngredientsForStepEvent.Done)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChooseIngredientsForStep() {
    WeekOnAPlateTheme {
        val vm = ChooseIngredientsForStepViewModel()
        LaunchedEffect(true) {
            vm.launchAndGet(recipes[0].ingredients, listOf(0, 2)) {}
        }
        ChooseIngredientsForStep(vm)
    }
}
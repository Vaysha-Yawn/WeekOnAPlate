package week.on.a.plate.dialogs.chooseIngredientsForStep.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.example.recipes
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.dialogs.chooseIngredientsForStep.event.ChooseIngredientsForStepEvent
import week.on.a.plate.dialogs.chooseIngredientsForStep.state.ChooseIngredientsForStepUIState
import week.on.a.plate.core.uitools.clickNoRipple


@Composable
fun ChooseIngredientsForStep(state: ChooseIngredientsForStepUIState, onEvent: (ChooseIngredientsForStepEvent) -> Unit) {
    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier =  Modifier.padding(bottom = 12.dp)) {
            TextTitle(
                text = stringResource(R.string.select_ingredients), textAlign = TextAlign.End)
        }

        LazyColumn() {
            itemsIndexed(state.ingredientsAll){ind, item->
                IngredientRow(onEvent, item, state)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        DoneButton(stringResource(R.string.done)) {
            onEvent(ChooseIngredientsForStepEvent.Done)
        }
    }
}

@Composable
private fun IngredientRow(
    onEvent: (ChooseIngredientsForStepEvent) -> Unit,
    item: IngredientInRecipeView,
    state: ChooseIngredientsForStepUIState
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickNoRipple { onEvent(ChooseIngredientsForStepEvent.ClickToIngredient(item.ingredientView.ingredientId)) }
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CheckBoxCustom(state, item, onEvent)
        if (item.ingredientView.img != "") {
            ImageLoad(
                url = item.ingredientView.img, modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
        Column(Modifier.weight(1f)) {
            //name
            TextBody(
                text = item.ingredientView.name,
                color = MaterialTheme.colorScheme.onBackground
            )
            //description
            if (item.description != "") {
                TextBodyDisActive(text = item.description)
            }
        }
        Measure(item)
    }
}

@Composable
private fun Measure(item: IngredientInRecipeView) {
    Row {
        val valueAndMeasure = getIngredientCountAndMeasure1000(
            LocalContext.current,
            item.count,
            item.ingredientView.measure
        )
        TextBody(
            text = valueAndMeasure.first, color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(5.dp))
        TextBody(text = valueAndMeasure.second, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun CheckBoxCustom(
    state: ChooseIngredientsForStepUIState,
    item: IngredientInRecipeView,
    onEvent: (ChooseIngredientsForStepEvent) -> Unit
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
}

@Preview(showBackground = true)
@Composable
fun PreviewChooseIngredientsForStep() {
    WeekOnAPlateTheme {
        val state = ChooseIngredientsForStepUIState(recipes[0].ingredients, remember {
            mutableStateOf(
                listOf(0, 2)
            )
        } )
        ChooseIngredientsForStep(state){}
    }
}
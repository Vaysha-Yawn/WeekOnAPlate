package week.on.a.plate.screens.additional.createRecipe.view.recipe

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.EditTextLine
import week.on.a.plate.core.uitools.ImageLoadEditable
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CommonButton
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.additional.createRecipe.state.RecipeCreateUIState
import week.on.a.plate.screens.additional.createRecipe.state.RecipeStepState


@Composable
fun StepRecipeEdit(
    index: Int,
    recipeStepState: RecipeStepState,
    state: RecipeCreateUIState,
    onEvent: (week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent) -> Unit
) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.delete),
                contentDescription = "Delete step",
                modifier = Modifier
                    .size(24.dp)
                    .clickNoRipple {
                        onEvent(
                            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteStep(
                                recipeStepState
                            )
                        )
                    }
            )
            Spacer(modifier = Modifier.width(6.dp))
            TextTitleItalic(text = (index + 1).toString() + stringResource(R.string.step_))
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
    EditTextLine(text = recipeStepState.description, placeholder = stringResource(R.string.enter_step_description))
    if (recipeStepState.image.value != "") {
        Spacer(modifier = Modifier.height(12.dp))
        Row {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "Remove step photo",
                modifier = Modifier.clickable {
                    onEvent(
                        week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.DeleteImage(
                            recipeStepState
                        )
                    )
                })
            Spacer(modifier = Modifier.width(12.dp))

            ImageLoadEditable(url = recipeStepState.image.value,
                recipeStepState.imageContainer,
                modifier = Modifier
                    .height(160.dp)
                    .clickNoRipple {
                        onEvent(
                            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditImage(
                                recipeStepState,
                                context
                            )
                        )
                    })
        }
    }

    if (recipeStepState.timer.longValue != 0L) {
        Spacer(modifier = Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = "Clear timer",
                modifier = Modifier.clickable {
                    onEvent(
                        week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.ClearTimer(
                            recipeStepState
                        )
                    )
                })
            Spacer(modifier = Modifier.width(12.dp))
            TimerButton(recipeStepState.timer.longValue.toInt()) {
                onEvent(
                    week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditTimer(
                        context,
                        recipeStepState
                    )
                )
            }
        }
    }
    if (recipeStepState.pinnedIngredientsInd.value.isNotEmpty()) {
        Spacer(modifier = Modifier.height(12.dp))
        PinnedIngredientsForStep(
            recipeStepState.pinnedIngredientsInd.value.map { id ->
                state.ingredients.value.find { it.ingredientView.ingredientId == id }!!
            }
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    CommonButton(stringResource(R.string.edit_pinned_ingredients)) {
        onEvent(
            week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditPinnedIngredients(
                recipeStepState
            )
        )
    }
}

@Composable
fun ImageStepButton(
    recipeStepState: RecipeStepState,
    onEvent: (week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent) -> Unit
) {
    val context = LocalContext.current
    Row(
        Modifier
            .clickable {
                onEvent(
                    week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditImage(
                        recipeStepState,
                        context
                    )
                )
            }
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.photo), contentDescription = "Add photo for step")
        Spacer(modifier = Modifier.width(5.dp))
        TextBody(text = stringResource(R.string.photo))
    }
    Spacer(modifier = Modifier.width(6.dp))
}

@Composable
fun TimerStep(
    recipeStepState: RecipeStepState,
    onEvent: (week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent) -> Unit
) {
    val context = LocalContext.current
    Row(
        Modifier
            .clickable {
                onEvent(
                    week.on.a.plate.screens.additional.createRecipe.event.RecipeCreateEvent.EditTimer(
                        context,
                        recipeStepState
                    )
                )
            }
            .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.timer), contentDescription = "Edit timer step")
        Spacer(modifier = Modifier.width(5.dp))
        TextBody(text = stringResource(R.string.timer))
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewStepRecipeEdit() {
    WeekOnAPlateTheme {
        val state = RecipeCreateUIState()
        Column {
            PinnedIngredientsForStep(state.steps.value[0].pinnedIngredientsInd.value.map { id ->
                state.ingredients.value.find { it.ingredientView.ingredientId == id }!!
            })
            StepRecipeEdit(0, state.steps.value[0], state) {}
            StepRecipeEdit(1, state.steps.value[1], state) {}
        }
    }
}
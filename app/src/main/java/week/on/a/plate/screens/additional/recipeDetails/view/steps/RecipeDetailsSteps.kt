package week.on.a.plate.screens.additional.recipeDetails.view.steps

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
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
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.theme.bodyMediumSemiBold
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.additional.createRecipe.view.recipe.PinnedIngredientsForStep
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.logic.utils.setTimer
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState

@Composable
fun RecipeDetailsSteps(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    Column(Modifier.background(MaterialTheme.colorScheme.background)) {
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextBody(text = stringResource(R.string.time_cook))
            if (state.recipe.steps.isNotEmpty()) {
                TextBody(
                    text = state.recipe.duration.toSecondOfDay().timeToString(LocalContext.current)
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)

        for ((index, step) in state.recipe.steps.withIndex()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 24.dp, vertical = 24.dp), horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    TextInApp(
                        text = (index + 1).toString(),
                        textStyle = bodyMediumSemiBold,
                        color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 12.dp)
                            .padding(vertical = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    TimerButton(step.timer.toInt())
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (step.image!="") {
                    ImageLoad(
                        url = step.image, modifier = Modifier
                            .align(Alignment.Start)
                            .height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextBody(text = step.description, Modifier.padding(start = 12.dp))
                state.mapPinnedStepIdToIngredients.value[step.id]?.let {
                    Spacer(modifier = Modifier.height(12.dp))
                    PinnedIngredientsForStep(it)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
fun TimerButton(timer: Int) {
    if (timer == 0) return
    val act = LocalContext.current
    Row(
        Modifier
            .clickNoRipple {
                setTimer(act, timer)
            }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "Timer",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        TextInApp(
            text = timer.timeToString(LocalContext.current),
            textStyle = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = R.drawable.play),
            contentDescription = "Start timer",
            modifier = Modifier.size(24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSteps() {
    WeekOnAPlateTheme {
        RecipeDetailsSteps(RecipeDetailsState().apply {
            recipe = recipeTom
        }) {}
    }
}
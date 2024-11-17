package week.on.a.plate.screens.cookPlanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.createRecipe.view.PinnedIngredientsForStep
import week.on.a.plate.screens.recipeDetails.view.steps.TimerButton
import java.time.LocalDateTime

@Composable
fun CardStep(step: CookPlannerStepView, onEvent: (Event) -> Unit) {
    Row(
        Modifier
            .clickable {
                onEvent(CookPlannerEvent.NavToFullStep(step))
            }
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(bottom = 24.dp, top = 12.dp)
            .padding(horizontal = 24.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Checkbox(
                    checked = step.checked,
                    modifier = Modifier.padding(0.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.secondary,
                        checkmarkColor = MaterialTheme.colorScheme.onBackground
                    ),
                    onCheckedChange = {
                        onEvent(CookPlannerEvent.CheckStep(step))
                    },
                )
                TextBody(
                    "${step.start.hour.normalizeTimeToText()}:${step.start.minute.normalizeTimeToText()} -> ${step.end.hour.normalizeTimeToText()}:${step.end.minute.normalizeTimeToText()}"
                )
                Spacer(Modifier.weight(1f))
                MoreButton {
                    onEvent(CookPlannerEvent.ShowStepMore(step))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                ImageLoad(
                    step.stepView.image,
                    Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clipToBounds()
                        .size(200.dp)
                        .scale(1.4f)
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            TextBodyDisActive(
                step.recipeName + ", ${step.portionsCount}" + stringResource(R.string._portions),
                modifier = Modifier.padding(start = 12.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextSmall(
                step.stepView.description, modifier = Modifier.padding(start = 12.dp)
            )
            if (step.stepView.ingredientsPinnedId.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                val ingredients = remember {
                    step.stepView.ingredientsPinnedId.map { id ->
                        //todo move to logic layer
                        val ingr =
                            step.allRecipeIngredientsByPortions.find { it.ingredientView.ingredientId == id }!!
                        val startIngredientCount = ingr.count
                        val stdPortions = step.stdPortionsCount
                        val newCountPortions = step.portionsCount
                        if (startIngredientCount > 0) {
                            ingr.count =
                                (startIngredientCount.toFloat() / stdPortions.toFloat() * newCountPortions).toInt()
                        }
                        ingr
                    }
                }
                PinnedIngredientsForStep(ingredients)
            }
            if (step.stepView.timer.toInt() != 0) {
                Spacer(modifier = Modifier.height(6.dp))
                TimerButton(step.stepView.timer.toInt())
            }
        }
    }
}

fun Int.normalizeTimeToText(): String {
    return if (this < 10) {
        "0$this"
    } else {
        this.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardStep() {
    WeekOnAPlateTheme {
        CardStep(
            CookPlannerStepView(
                0,
                0,
                0,
                "Паэлья",
                LocalDateTime.of(2024, 10, 24, 10, 45),
                LocalDateTime.of(2024, 10, 24, 11, 0),
                RecipeStepView(0, "Паэлью жарим до готовности", "", 15, 0, 0, listOf()),
                listOf(),
                false,
                3, 4
            )
        ) {}
    }
}
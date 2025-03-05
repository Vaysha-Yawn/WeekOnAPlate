package week.on.a.plate.screens.base.cookPlanner.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.data.dataView.CookPlannerGroupView
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.example.recipeExampleBase
import week.on.a.plate.screens.additional.createRecipe.view.recipe.PinnedIngredientsForStep
import week.on.a.plate.screens.additional.recipeDetails.view.steps.TimerButton
import week.on.a.plate.screens.base.cookPlanner.event.CookPlannerEvent
import java.time.LocalDateTime


@Composable
fun CardStep(step: CookPlannerStepView, onEvent: (Event) -> Unit, index:Int) {
    Row {
        Column {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
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
                TextBody(stringResource(R.string.step)+" "+ (index+1).toString())
            }
            Column(Modifier.padding(start = 12.dp)) {
                TextBody(
                    step.stepView.description
                )
            }
            if (step.stepView.ingredientsPinnedId.isNotEmpty()) {
                Spacer(modifier = Modifier.height(6.dp))
                PinnedIngredientsForStep(step.pinnedIngredientsByPortionsCount)
            }
            if (step.stepView.timer.toInt() != 0) {
                Spacer(modifier = Modifier.height(12.dp))
                TimerButton(step.stepView.timer.toInt())
            }
        }
    }
}


@Composable
fun CookGroup(group: CookPlannerGroupView, onEvent: (Event) -> Unit) {
    val context = LocalContext.current
    Column(Modifier
        .clickNoRipple {
            onEvent(CookPlannerEvent.NavToFullStep(group))
        }
        .padding(horizontal = 12.dp)
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
        .padding(bottom = 24.dp, top = 12.dp)
        .padding(horizontal = 12.dp)) {
        Column(Modifier.padding(start = 12.dp)) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextTitle(group.recipeName +", "+ + group.portionsCount +" "+ stringResource(R.string.portions_end_of_phrase))
                MoreButton {
                    onEvent(CookPlannerEvent.ShowStepMore(group, context))
                }
            }
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painterResource(R.drawable.time),
                    "Time",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.width(12.dp))
                TextBody(
                    "${group.start.hour.normalizeTimeToText()}:${group.start.minute.normalizeTimeToText()} -> ${group.end.hour.normalizeTimeToText()}:${group.end.minute.normalizeTimeToText()}"
                )
            }
        }
        for ((ind, step) in group.steps.withIndex()) {
            Spacer(Modifier.height(12.dp))
            CardStep(step, onEvent, ind)
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
        CookGroup(
            CookPlannerGroupView(
                0,
                0,
                LocalDateTime.of(2024, 5, 31, 4, 0),
                LocalDateTime.of(2024, 5, 31, 5, 0),
                "Курица  с рисом",
                4,
                recipeExampleBase[0].steps.map {
                    CookPlannerStepView(
                        0,
                        0,
                        it,
                        false,
                        recipeExampleBase[0].ingredients,
                    )
                })
        ) {}
    }
}
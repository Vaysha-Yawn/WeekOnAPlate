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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.data.dataView.CookPlannerStepView
import week.on.a.plate.data.dataView.recipe.RecipeStepView
import week.on.a.plate.screens.cookPlanner.event.CookPlannerEvent
import week.on.a.plate.screens.recipeDetails.view.steps.TimerButton
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun CardStep(step: CookPlannerStepView, onEvent:(Event)->Unit) {
    Row(
        Modifier
            .clickable {
                onEvent(CookPlannerEvent.NavToFullStep(step))
            }
            .padding(horizontal = 12.dp).fillMaxWidth().background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(bottom = 24.dp, top = 12.dp).padding( horizontal = 24.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
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
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                if (step.stepView.image.startsWith("http")){
                    ImageLoad(
                        step.stepView.image,
                        Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .clipToBounds()
                            .size(40.dp)
                            .scale(1.6f)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                }
                Column(
                    Modifier,
                    horizontalAlignment = Alignment.Start,
                ) {
                    Spacer(modifier = Modifier.height(12.dp))
                    TextTitle(
                        step.recipeName
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TextBody(
                        step.stepView.description
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    TimerButton(step.stepView.timer.toInt())
                }
            }
        }
    }
}

fun Int.normalizeTimeToText():String{
    return if(this<10){
        "0$this"
    }else{
        this.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardStep() {
    WeekOnAPlateTheme {
        CardStep(
            CookPlannerStepView(
                0, 0, 0, "Паэлья", LocalDateTime.of(2024, 10, 24, 10, 45),
                LocalDateTime.of(2024, 10, 24, 11, 0),
                RecipeStepView(0, "Паэлью жарим до готовности", "", 15, LocalTime.of(0, 15)), false
            )
        ) {}
    }
}
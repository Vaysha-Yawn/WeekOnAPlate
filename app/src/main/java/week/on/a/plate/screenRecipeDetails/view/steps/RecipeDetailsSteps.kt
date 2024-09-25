package week.on.a.plate.screenRecipeDetails.view.steps

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.R
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.ColorBackgroundYellow
import week.on.a.plate.core.theme.ColorPanelYellow
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.theme.bodyMediumSemiBold

@Composable
fun RecipeDetailsSteps(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    Column {
        if (state.recipe.value == null) return
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextBody(text = state.recipe.value!!.prepTime.toString() + " мин")
                TextBody(text = "Активное время")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextBody(text = state.recipe.value!!.prepTime.toString() + " мин")
                TextBody(text = "Всё время")
            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(24.dp))
        val listGradient = listOf(Color(0xFFFFEADE), Color(0xFFFFF2DE), Color(0xFFFFFFFF))
        for ((index, step) in state.recipe.value!!.steps.withIndex()) {
            HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listGradient))
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    TextInApp(
                        text = index.toString(),
                        textStyle = bodyMediumSemiBold,
                        color = Color(0xFF4D2222), modifier = Modifier
                            .background(
                                ColorBackgroundYellow, CircleShape
                            )
                            .border(1.dp, ColorPanelYellow, CircleShape)
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    TimerButton(step.timer, onEvent)
                }
                Spacer(modifier = Modifier.height(24.dp))
                if (step.image.startsWith("http")) {
                    AsyncImage(
                        model = step.image,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        placeholder = painterResource(
                            id = R.drawable.time
                        )
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                }
                TextSmall(text = step.description, color = ColorTextBlack)
            }
            HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

}

@Composable
fun TimerButton(timer: Long, onEvent: (RecipeDetailsEvent) -> Unit) {
    if (timer == 0L) return
    val act = LocalContext.current
    Row(
        Modifier
            .clickable {
                onEvent(RecipeDetailsEvent.StartTimerForStep(timer, act))
            }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp), verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        TextInApp(
            text = timeToString(timer),
            textStyle = Typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.width(12.dp))
        Image(
            painter = painterResource(id = R.drawable.play),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
    }
}

fun timeToString(time: Long): String {
    val sec = time.toInt()
    val min = (sec / 60)
    val hour = min / 60
    return "${if (hour < 10) "0" else ""}$hour:${if (min < 10) "0" else ""}$min:${if (sec < 10) "0" else ""}$sec"
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSteps() {
    WeekOnAPlateTheme {
        RecipeDetailsSteps(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }) {}
    }
}
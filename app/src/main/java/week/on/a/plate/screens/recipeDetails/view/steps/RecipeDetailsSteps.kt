package week.on.a.plate.screens.recipeDetails.view.steps

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.Typography
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.theme.bodyMediumSemiBold
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.utils.timeToString
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.createRecipe.view.PinnedIngredientsForStep
import week.on.a.plate.screens.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.recipeDetails.logic.setTimer
import week.on.a.plate.screens.recipeDetails.state.RecipeDetailsState

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
            //todo?

            /*Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextBody(text = state.recipe.value.prepTime.toInt().timeToString())
                TextBody(text = "Активное время")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TextBody(text = state.recipe.value.allTime.toInt().timeToString())
                TextBody(text = "Всё время")
            }*/
            TextBody(text = "Время приготовления: ")
            if (state.recipe.value.steps.isNotEmpty()) {
                TextBody(
                    text = state.recipe.value.steps.maxOf { it.start + it.duration }.toInt()
                        .timeToString()
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)

        Spacer(modifier = Modifier
            .height(24.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface))
        for ((index, step) in state.recipe.value.steps.withIndex()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 24.dp, vertical = 12.dp), horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextInApp(
                        text = (index + 1).toString(),
                        textStyle = bodyMediumSemiBold,
                        color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.background,
                                RoundedCornerShape(5.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 5.dp)
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                    TimerButton(step.timer.toInt())
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (step.image.startsWith("http")) {
                    ImageLoad(
                        url = step.image, modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .height(200.dp)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextBody(text = step.description, modifier = Modifier.padding(start = 12.dp))
                Spacer(modifier = Modifier.height(12.dp))
                val listPinned = remember {
                    step.ingredientsPinnedId.map { id->
                        //todo move to logic layer
                        val ingr = state.recipe.value.ingredients.find { it.ingredientView.ingredientId == id }!!
                        val startIngredientCount = ingr.count
                        val stdPortions = state.recipe.value.standardPortionsCount
                        val newCountPortions = state.currentPortions.intValue
                        if (startIngredientCount > 0) {
                            ingr.count = (startIngredientCount.toFloat() / stdPortions.toFloat() * newCountPortions).toInt()
                        }
                        ingr
                    }
                }
                PinnedIngredientsForStep(listPinned)
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
            .clickable {
                setTimer(act, timer)
            }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(50.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50.dp))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        TextInApp(
            text = timer.timeToString(),
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

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsSteps() {
    WeekOnAPlateTheme {
        RecipeDetailsSteps(RecipeDetailsState().apply {
            recipe = mutableStateOf(recipeTom)
        }) {}
    }
}
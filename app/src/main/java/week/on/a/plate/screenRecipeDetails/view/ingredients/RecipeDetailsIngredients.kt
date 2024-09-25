package week.on.a.plate.screenRecipeDetails.view.ingredients

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import week.on.a.plate.R
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall
import week.on.a.plate.screenRecipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screenRecipeDetails.state.RecipeDetailsState
import week.on.a.plate.core.theme.ColorPanelYellow
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.theme.WeekOnAPlateTheme

@Composable
fun RecipeDetailsIngredients(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    val statePortions = remember {
        mutableIntStateOf(state.recipe.value!!.standardPortionsCount)
    }
    Column {
        if (state.recipe.value == null) return
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {

            TextBody(text = "Ингредиенты на")
            ButtonsCounterSmall(value = statePortions, minus = {}, plus = { })
            TextBody(text = "порций")
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(24.dp))
        val listGradient = listOf(Color(0xFFFFEADE), Color(0xFFFFF2DE), Color(0xFFFFFFFF))
        for ((index, ingredient) in state.recipe.value!!.ingredients.withIndex()) {
            HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(listGradient))
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (ingredient.ingredientView.img.startsWith("http")) {
                    AsyncImage(
                        model = ingredient.ingredientView.img,
                        contentDescription = "",
                        modifier = Modifier
                            .height(40.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        placeholder = painterResource(
                            id = R.drawable.time
                        ), clipToBounds = true
                    )
                    Spacer(modifier = Modifier.width(24.dp))
                }
                Column(Modifier.weight(1f)) {
                    TextBody(text = ingredient.ingredientView.name, color = ColorTextBlack)
                    if (ingredient.description != "") {
                        TextBodyDisActive(text = ingredient.description)
                    }
                }
                Row {
                    TextBody(
                        text = if (ingredient.count.toString() == ingredient.count.toInt()
                                .toDouble().toString()
                        ) {
                            ingredient.count.toInt().toString()
                        } else {
                            ingredient.count.toString()
                        }, color = ColorTextBlack
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextBody(text = ingredient.ingredientView.measure, color = ColorTextBlack)
                }
            }
            HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsIngredients() {
    WeekOnAPlateTheme {
        RecipeDetailsIngredients(RecipeDetailsState().apply {
            recipe.value = recipeTom
        }) {}
    }
}
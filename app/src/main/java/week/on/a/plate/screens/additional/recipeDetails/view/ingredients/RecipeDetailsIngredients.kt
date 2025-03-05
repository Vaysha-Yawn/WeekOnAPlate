package week.on.a.plate.screens.additional.recipeDetails.view.ingredients

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.ButtonsCounterSmall
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent
import week.on.a.plate.screens.additional.recipeDetails.state.RecipeDetailsState


@Composable
fun RecipeDetailsIngredients(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    Column {
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            TextBody(text = stringResource(R.string.ingredients_for))
            ButtonsCounterSmall(value = state.currentPortions, minus = {
                onEvent(RecipeDetailsEvent.MinusPortionsView)
            }, plus = {
                onEvent(RecipeDetailsEvent.PlusPortionsView)
            })
            TextBody(text = stringResource(R.string._portions))
        }
        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
        Spacer(modifier = Modifier.height(12.dp))

        for ((index, ingredient) in state.recipe.ingredients.withIndex()) {
            IngredientInRecipeCard(
                ingredient,
                state.ingredientsCounts.value[index].count,
                isDeletable = false
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipeDetailsIngredients() {
    WeekOnAPlateTheme {
        RecipeDetailsIngredients(RecipeDetailsState().apply {
            recipe = recipeTom
        }) {}
    }
}
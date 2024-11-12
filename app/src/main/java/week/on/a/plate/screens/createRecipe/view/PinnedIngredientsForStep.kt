package week.on.a.plate.screens.createRecipe.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

@Composable
fun PinnedIngredientsForStep(
    listAdded: List<IngredientInRecipeView>,
) {
    LazyRow {
        items(listAdded.size) {
            val item = listAdded[it]
            Row(
                Modifier
                    .background(MaterialTheme.colorScheme.background, RoundedCornerShape(20.dp))
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    TextSmall(item.ingredientView.name)
                    if (item.description != "") {
                        TextSmall(item.description)
                    }
                }
                Spacer(modifier = Modifier.width(5.dp))
                val valueAndMeasure =
                    getIngredientCountAndMeasure1000(item.count, item.ingredientView.measure)
                TextBody(
                    text = valueAndMeasure.first, color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextBody(
                    text = valueAndMeasure.second,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
        }
    }
}
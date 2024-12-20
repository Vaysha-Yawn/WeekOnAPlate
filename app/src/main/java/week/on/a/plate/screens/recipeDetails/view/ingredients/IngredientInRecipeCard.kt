package week.on.a.plate.screens.recipeDetails.view.ingredients

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView


@Composable
fun IngredientInRecipeCard(
    ingredient: IngredientInRecipeView,
    count: Int? = null,
    click: () -> Unit = {},
    isDeletable:Boolean,
    delete: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable { click() }
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (ingredient.ingredientView.img.startsWith("http")) {
            ImageLoad(
                url = ingredient.ingredientView.img, modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
        Column(Modifier.weight(1f)) {
            TextBody(text = ingredient.ingredientView.name, color = MaterialTheme.colorScheme.onBackground)
            if (ingredient.description != "") {
                TextBodyDisActive(text = ingredient.description)
            }
        }
        Row {
            val valueAndMeasure = getIngredientCountAndMeasure1000(LocalContext.current, count?:ingredient.count, ingredient.ingredientView.measure)
            TextBody(
                text = valueAndMeasure.first, color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextBody(text = valueAndMeasure.second, color = MaterialTheme.colorScheme.onBackground)
        }
        if (isDeletable) {
            Spacer(modifier = Modifier.width(5.dp))
            Icon(
                painterResource(R.drawable.delete),
                "",
                modifier = Modifier.clickable {
                    delete()
                })
        }
    }
}
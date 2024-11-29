package week.on.a.plate.core.uitools.ingredientCard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.data.dataView.example.ingredientTomato
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.screens.filters.view.clickNoRipple

@Composable
fun CardIngredient(
    ingredient: IngredientView,
    endAction: @Composable () -> Unit,
    actionClick:()->Unit
) {
    Card(
        Modifier.fillMaxWidth().clickNoRipple(actionClick),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RectangleShape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 24.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.add_food),
                    contentDescription = ingredient.name,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                TextBody(text = ingredient.name)
            }
            endAction()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCardIngredient() {
    WeekOnAPlateTheme {
        CardIngredient(ingredientTomato, {}){}
    }
}

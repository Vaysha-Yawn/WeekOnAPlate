package week.on.a.plate.screenRecipeDetails.view.ingredients

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.theme.ColorPanelYellow
import week.on.a.plate.core.theme.ColorTextBlack
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IngredientInRecipeCard(
    ingredient: IngredientInRecipeView,
    count: Int? = null,
    click: () -> Unit = {},
    longClick: () -> Unit = {},
) {
    val listGradient = listOf(Color(0xFFFFEADE), Color(0xFFFFF2DE), Color(0xFFFFFFFF))
    HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
    Row(
        Modifier
            .fillMaxWidth().combinedClickable (
                onClick = click,
                onLongClick = longClick
            )
            .background(Brush.horizontalGradient(listGradient))
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
            TextBody(text = ingredient.ingredientView.name, color = ColorTextBlack)
            if (ingredient.description != "") {
                TextBodyDisActive(text = ingredient.description)
            }
        }
        Row {
            TextBody(
                text = (count?:ingredient.count).toString(), color = ColorTextBlack
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextBody(text = ingredient.ingredientView.measure, color = ColorTextBlack)
        }
    }
    HorizontalDivider(thickness = 1.dp, color = ColorPanelYellow)
}
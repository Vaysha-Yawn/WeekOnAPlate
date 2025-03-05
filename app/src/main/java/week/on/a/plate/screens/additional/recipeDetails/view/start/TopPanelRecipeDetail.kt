package week.on.a.plate.screens.additional.recipeDetails.view.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.screens.additional.recipeDetails.event.RecipeDetailsEvent

@Composable
fun TopPanelRecipeDetail(inFavorite: Boolean, onEvent: (RecipeDetailsEvent) -> Unit) {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ImageButton(R.drawable.back) {
            onEvent(RecipeDetailsEvent.Back)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageButton(R.drawable.share) {
                onEvent(RecipeDetailsEvent.Share(context))
            }
            ImageButton(R.drawable.delete) {
                onEvent(RecipeDetailsEvent.Delete(context))
            }
            ImageButton(R.drawable.edit) {
                onEvent(RecipeDetailsEvent.Edit)
            }
            ImageButton(
                if (inFavorite) R.drawable.bookmark_full else R.drawable.bookmark
            ) {
                onEvent(RecipeDetailsEvent.SwitchFavorite)
            }
            ImageButton(R.drawable.add_shopping_cart) {
                onEvent(RecipeDetailsEvent.AddToCart(context))
            }
        }
    }
    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outline)
}

@Composable
fun ImageButton(res: Int, modifier: Modifier = Modifier, action: () -> Unit) {
    Image(
        painter = painterResource(id = res),
        contentDescription = "Image",
        modifier = modifier
            .clickable {
                action()
            }
            .padding(12.dp)
            .size(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopPanelRecipeDetail() {
    WeekOnAPlateTheme {
        TopPanelRecipeDetail(recipeTom.inFavorite) {}
    }
}
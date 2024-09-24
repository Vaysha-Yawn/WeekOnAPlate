package week.on.a.plate.recipeFullScreen.view.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.buttons.BackButton
import week.on.a.plate.recipeFullScreen.event.RecipeDetailsEvent
import week.on.a.plate.recipeFullScreen.state.RecipeDetailsState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@Composable
fun TopPanelRecipeDetail(state: RecipeDetailsState, onEvent: (RecipeDetailsEvent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        ImageButton(R.drawable.back) {

        }
        Row {
            ImageButton(R.drawable.edit) {

            }
            ImageButton(R.drawable.add_shopping_cart) {

            }
            ImageButton(R.drawable.bookmark) {

            }
            ImageButton(R.drawable.add) {

            }
        }
    }
}

@Composable
fun ImageButton(res:Int, modifier: Modifier = Modifier, action:()->Unit){
    Image(
        painter = painterResource(id = res),
        contentDescription = "",
        modifier = modifier.clickable {
            action()
        }.padding(12.dp).size(24.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopPanelRecipeDetail() {
    WeekOnAPlateTheme {
        TopPanelRecipeDetail(RecipeDetailsState()) {}
    }
}
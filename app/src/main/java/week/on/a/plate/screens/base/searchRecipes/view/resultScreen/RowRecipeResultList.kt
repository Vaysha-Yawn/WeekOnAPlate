package week.on.a.plate.screens.base.searchRecipes.view.resultScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.PlusButtonCard
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent

@Composable
fun RowRecipeResultList(
    recipeView: RecipeView,
    onEvent: (SearchScreenEvent) -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickNoRipple {
                onEvent(
                    SearchScreenEvent.NavigateToFullRecipe(recipeView)
                )
            },
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RectangleShape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (recipeView.img!="") {
                ImageLoad(
                    recipeView.img, Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .scale(1.5f)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .background(ColorButtonNegativeGrey, RoundedCornerShape(20.dp))
                        .size(80.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.weight(2f)) {
                TextBody(text = recipeView.name, Modifier.padding(start = 6.dp))
                Spacer(modifier = Modifier.height(12.dp))
                TagListHidden(
                    recipeView.tags,
                    recipeView.ingredients.map { it -> it.ingredientView })
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround) {
                val inFavorite = recipeView.inFavorite
                Image(
                    painter = painterResource(
                        id = if (inFavorite) {
                            R.drawable.bookmark_full
                        } else R.drawable.bookmark
                    ),
                    contentDescription = "In favorite",
                    modifier = Modifier
                        .size(36.dp)
                        .clickNoRipple {
                            onEvent(
                                SearchScreenEvent.FlipFavorite(
                                    recipeView,
                                    inFavorite
                                )
                            )
                        },
                )
                Spacer(modifier = Modifier.height(24.dp))
                val context = LocalContext.current
                PlusButtonCard {
                    onEvent(
                        SearchScreenEvent.AddToMenu(recipeView, context)
                    )
                }
            }
        }
    }
}
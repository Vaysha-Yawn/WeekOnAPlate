package week.on.a.plate.screens.base.searchRecipes.view.resultScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.PlusButtonCard
import week.on.a.plate.core.uitools.clickNoRipple
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screens.base.searchRecipes.event.SearchScreenEvent

@Composable
fun CardRecipeResult(recipe: RecipeView, onEvent: (SearchScreenEvent) -> Unit, modifier: Modifier) {
    Box(
        modifier
            .clickNoRipple {
                onEvent(
                    SearchScreenEvent.NavigateToFullRecipe(recipe)
                )
            }
            .padding(bottom = 12.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp)), contentAlignment = Alignment.TopCenter
    ) {
        if (recipe.img!="") {
            ImageLoad(
                recipe.img,
                Modifier
                    .clipToBounds()
                    .scale(2f)
                    .height(150.dp)
            )
        } else {
            Spacer(Modifier.height(150.dp))
        }
        Column(
            Modifier
                .fillMaxWidth()
                .clipToBounds(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(end = 12.dp),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                val inFavorite = recipe.inFavorite

                Box(
                    Modifier
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(5.dp)
                ) {
                    val context = LocalContext.current
                    PlusButtonCard {
                        onEvent(
                            SearchScreenEvent.AddToMenu(recipe, context)
                        )
                    }
                }
                Spacer(modifier = Modifier.size(24.dp))
                Box(
                    Modifier
                        .background(
                            MaterialTheme.colorScheme.surface,
                            RoundedCornerShape(10.dp)
                        )
                        .padding(5.dp)
                ) {
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
                                        recipe,
                                        inFavorite
                                    )
                                )
                            },
                    )
                }
            }
            Spacer(Modifier.height(30.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextBody(
                    recipe.name
                )
                Spacer(modifier = Modifier.size(12.dp))
                TagListHidden(
                    recipe.tags,
                    recipe.ingredients.map { it -> it.ingredientView })
            }
        }
    }
}
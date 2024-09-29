package week.on.a.plate.screenSearchRecipes.view.resultScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.TagSmall
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.PlusButtonCard
import week.on.a.plate.data.dataView.example.recipeTom
import week.on.a.plate.data.dataView.recipe.IngredientView
import week.on.a.plate.data.dataView.recipe.RecipeTagView
import week.on.a.plate.data.dataView.recipe.RecipeView
import week.on.a.plate.screenSearchRecipes.event.SearchScreenEvent

@Composable
fun SearchResultScreen(
    result: List<RecipeView>,
    onEvent: (SearchScreenEvent) -> Unit
) {
    LazyColumn {
        items(result.size) {
            RowRecipeResultCard(result[it], onEvent)
        }
    }
}

@Composable
fun RowRecipeResultCard(
    recipeView: RecipeView,
    onEvent: (SearchScreenEvent) -> Unit
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable {
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
                .padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (recipeView.img.startsWith("http")) {
                ImageLoad(
                    recipeView.img, Modifier
                        .size(85.dp)
                        .clip(CircleShape)
                        .background(ColorButtonNegativeGrey, CircleShape)
                        .scale(1.5f)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .background(ColorButtonNegativeGrey, CircleShape)
                        .size(85.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.weight(2f)) {
                TextBody(text = recipeView.name)
                Spacer(modifier = Modifier.height(12.dp))
                TagList(recipeView.tags, recipeView.ingredients.map { it -> it.ingredientView })
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(Modifier.fillMaxHeight(), verticalArrangement = Arrangement.SpaceAround) {
                val inFavorite = recipeView.tags.find { tag -> tag.tagName == "Избранное" } != null
                Image(
                    painter = painterResource(
                        id = if (inFavorite) {
                            R.drawable.bookmark_full
                        } else R.drawable.bookmark
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onEvent(
                                SearchScreenEvent.FlipFavorite(
                                    recipeView.id,
                                    inFavorite
                                )
                            )
                        },
                )
                Spacer(modifier = Modifier.height(24.dp))
                PlusButtonCard {
                    onEvent(
                        SearchScreenEvent.AddToMenu(recipeView)
                    )
                }
            }
        }
    }
}


@Composable
fun TagList(tags: List<RecipeTagView>, ingredients: List<IngredientView>) {
    Column {
        if (tags.isNotEmpty()) {
            CustomGridTagsFlow(sizeList = tags.size) { index ->
                TagSmall(tag = tags[index])
                Spacer(modifier = Modifier.width(6.dp))
            }
        }
        if (ingredients.isNotEmpty()) {
            CustomGridTagsFlow(ingredients.size) { index ->
                TagSmall(ingredientView = ingredients[index])
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomGridTagsFlow(sizeList: Int, content: @Composable (Int) -> Unit) {
    FlowRow(Modifier.fillMaxWidth()) {
        for (i in 0 until sizeList) {
            content(i)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSearchResult() {
    WeekOnAPlateTheme {
        Column {
            SearchResultScreen(listOf(recipeTom, recipeTom, recipeTom, recipeTom)) {}
        }
    }
}
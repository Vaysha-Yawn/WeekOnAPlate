package week.on.a.plate.screens.menu.view.week.positionsCard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.data.dataView.example.ingredientTomato
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.menu.state.MenuIUState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekIngredientPosition(
    ingredient: Position.PositionIngredientView,
    onEvent: (event: Event) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditOtherPosition(ingredient))
                },
                onLongClick = { onEvent(week.on.a.plate.screens.menu.event.MenuEvent.SwitchEditMode) },
            )
            .clip(RoundedCornerShape(20.dp)),
    ) {
        if (ingredient.ingredient.ingredientView.img.startsWith("http")) {
            ImageLoad(
                ingredient.ingredient.ingredientView.img,
                Modifier
                    .clipToBounds()
                    .fillMaxWidth()
                    .height(150.dp)
                    .scale(2f)
            )
        } else {
            Spacer(Modifier.height(150.dp))
        }
        Row(
            Modifier
                .padding(top = 12.dp)
                .padding(horizontal = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(Modifier)
            MoreButtonWithBackg {
                onEvent(week.on.a.plate.screens.menu.event.MenuEvent.EditPositionMore(ingredient))
            }
        }
        Column {
            Spacer(Modifier.height(150.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clipToBounds()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextTitle(
                    ingredient.ingredient.ingredientView.name
                )
                if (ingredient.ingredient.description != "") {
                    Spacer(Modifier.height(6.dp))
                    TextBodyDisActive(
                        ingredient.ingredient.description,
                    )
                }
                val ingredientCount = getIngredientCountAndMeasure1000(ingredient.ingredient.count, ingredient.ingredient.ingredientView.measure)
                Spacer(Modifier.height(6.dp))
                SubText(
                    ingredientCount.first +" "+ ingredientCount.second
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeekIngredientPosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val posIngredient = Position.PositionIngredientView(
            0,
            IngredientInRecipeView(0, ingredientTomato, "ssss", 0),
            0
        )
        LazyVerticalGrid(GridCells.Fixed(2), Modifier.fillMaxWidth()) {
            item {
                WeekCardPosition(
                    posIngredient,
                    menuIUState = menuIUState
                ) {}
            }
            item {
                WeekCardPosition(
                    posIngredient,
                    menuIUState =menuIUState
                ) {}
            }
        }
    }
}
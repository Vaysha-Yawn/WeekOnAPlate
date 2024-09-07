package week.on.a.plate.menuScreen.view.recipeBlock

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.shortRecipe
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.TextInApp
import week.on.a.plate.core.uitools.TextInAppColored
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.ui.theme.ColorButtonGreen
import week.on.a.plate.ui.theme.Typography
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipePosition(
    recipe: Position.PositionRecipeView,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    Card(
        Modifier
            .padding(bottom = 10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RectangleShape
    ) {
        Box {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (menuIUState.editing.value) {
                    val state = menuIUState.chosenRecipes[recipe.id]
                    if (state != null) {
                        CheckButton(state) {
                            onEvent(MenuEvent.CheckRecipe(recipe.id))
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    } else {
                        onEvent(MenuEvent.AddCheckState(recipe.id))
                    }
                }
                Column(
                    Modifier.weight(3f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(Modifier.padding(bottom = 3.dp)) {
                        TextInApp(
                            "${recipe.portionsCount} Порции"
                        )
                    }
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextInApp(
                            recipe.recipe.name,
                            maxLines = 1,
                            modifier = Modifier.combinedClickable(
                                onClick = {
                                    onEvent(MenuEvent.NavToFullRecipe(recipe.recipe))
                                },
                                onLongClick =
                                { onEvent(MenuEvent.SwitchEditMode) },
                            ),
                            textStyle = Typography.bodyMedium
                        )
                    }
                }
                MoreButton {
                    onEvent(MenuEvent.Edit(recipe.id))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipePosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample

        Column {
            RecipePosition(
                Position.PositionRecipeView(
                    0,
                    shortRecipe,
                    1,
                ), menuIUState, {})
            RecipePosition(
                Position.PositionRecipeView(
                    0,
                    shortRecipe,
                    2,
                ), menuIUState, {})
            RecipePosition(
                Position.PositionRecipeView(
                    0,
                    shortRecipe,
                    3,
                ), menuIUState, {})
        }

    }
}

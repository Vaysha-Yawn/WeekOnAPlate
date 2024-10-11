package week.on.a.plate.screenMenu.view.week.positionsCard

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.data.dataView.example.shortRecipe
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.event.NavFromMenuData
import week.on.a.plate.screenMenu.event.SelectedEvent
import week.on.a.plate.screenMenu.state.MenuIUState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekRecipePosition(
    recipe: Position.PositionRecipeView,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit,
) {
    Box(
        Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    onEvent(
                        MenuEvent.NavigateFromMenu(
                            NavFromMenuData.NavToFullRecipe(
                                recipe.recipe.id, recipe.portionsCount
                            )
                        )
                    )
                },
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
            )
            .clip(RoundedCornerShape(20.dp)),
    ) {
        if (recipe.recipe.image.startsWith("http")) {
            ImageLoad(
                recipe.recipe.image,
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
            val sizeCheckBox = remember { mutableIntStateOf(0) }
            val animatedSize = animateIntAsState(sizeCheckBox.intValue)
            if (menuIUState.editing.value) {
                sizeCheckBox.intValue = 48
            } else {
                sizeCheckBox.intValue = 0
            }
            var state = menuIUState.chosenRecipes[recipe]
            if (state == null) {
                state = remember { mutableStateOf(false) }
                menuIUState.chosenRecipes[recipe] = state
            }
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .size(animatedSize.value.dp)
                    .animateContentSize(),
                contentAlignment = Alignment.Center
            ) {
                CheckButton(state) {
                    onEvent(MenuEvent.ActionSelect(SelectedEvent.CheckRecipe(recipe)))
                }
            }
            MoreButtonWithBackg {
                onEvent(MenuEvent.EditPositionMore(recipe))
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
                    recipe.recipe.name
                )
                Spacer(modifier = Modifier.size(10.dp))
                SubText(
                    "${recipe.portionsCount} " + stringResource(id = R.string.Portions)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeekRecipePosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val posRecipe = Position.PositionRecipeView(0, shortRecipe, 2, 0)
        LazyVerticalGrid(GridCells.Fixed(2), Modifier.fillMaxWidth()) {
            item {
                WeekCardPosition(
                    posRecipe,
                    menuIUState= menuIUState
                ) {}
            }
            item {
                WeekCardPosition(
                    posRecipe,
                    menuIUState=menuIUState
                ) {}
            }
        }
    }
}
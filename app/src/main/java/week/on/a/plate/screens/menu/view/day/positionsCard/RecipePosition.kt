package week.on.a.plate.screens.menu.view.day.positionsCard

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
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.data.dataView.example.shortRecipe
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.event.NavFromMenuData
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.menu.state.MenuUIState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekRecipePosition(
    recipe: Position.PositionRecipeView,
    menuUIState: MenuUIState,
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
                onLongClick = { onEvent(WrapperDatePickerEvent.SwitchEditMode) },
            )
            .clip(RoundedCornerShape(20.dp)),
    ) {
        val height = if (recipe.recipe.image!="") 150.dp else 50.dp
        if (recipe.recipe.image!="") {
            ImageLoad(
                recipe.recipe.image,
                Modifier
                    .clipToBounds()
                    .fillMaxWidth()
                    .height(height)
                    .scale(2f)
            )
        } else {
            Spacer(Modifier.height(height))
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
            if (menuUIState.wrapperDatePickerUIState.isGroupSelectedModeActive.value) {
                sizeCheckBox.intValue = 48
            } else {
                sizeCheckBox.intValue = 0
            }
            var state = menuUIState.chosenRecipes[recipe]
            if (state == null) {
                state = remember { mutableStateOf(false) }
                menuUIState.chosenRecipes[recipe] = state
            }
            Box(
                Modifier
                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                    .size(animatedSize.value.dp)
                    .animateContentSize(),
                contentAlignment = Alignment.Center
            ) {
                CheckButton(state) {
                    onEvent(
                        MenuEvent.ActionSelect(
                            week.on.a.plate.screens.menu.event.SelectedEvent.CheckRecipe(
                                recipe
                            )
                        )
                    )
                }
            }
            MoreButtonWithBackg {
                onEvent(MenuEvent.EditPositionMore(recipe))
            }
        }
        Column {
            Spacer(Modifier.height(height))
            Column(
                Modifier
                    .fillMaxWidth()
                    .clipToBounds()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextBody(
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
        val menuUIState = MenuUIState.MenuUIStateExample
        val posRecipe = Position.PositionRecipeView(0, shortRecipe, 2, 0)
        LazyVerticalGrid(GridCells.Fixed(2), Modifier.fillMaxWidth()) {
            item {
                WeekCardPosition(
                    posRecipe,
                    menuUIState = menuUIState
                ) {}
            }
            item {
                WeekCardPosition(
                    posRecipe,
                    menuUIState = menuUIState
                ) {}
            }
        }
    }
}
package week.on.a.plate.screens.menu.view.week.positionsList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.screens.menu.event.MenuEvent
import week.on.a.plate.screens.menu.event.NavFromMenuData
import week.on.a.plate.screens.wrapperDatePicker.event.WrapperDatePickerEvent
import week.on.a.plate.screens.menu.state.MenuUIState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RecipePosition(
    recipe: Position.PositionRecipeView,
    menuUIState: MenuUIState,
    onEvent: (event: Event) -> Unit,
    rowScope: RowScope
) {
    val context = LocalContext.current
    with(rowScope) {
        Row(
            Modifier
                .weight(3f)
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
                    onLongClick =
                    { onEvent(WrapperDatePickerEvent.SwitchEditMode) },
                )
                .padding(vertical = 5.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            if (menuUIState.wrapperDatePickerUIState.isGroupSelectedModeActive.value) {
                var state = menuUIState.chosenRecipes[recipe]
                if (state == null) {
                    state = remember { mutableStateOf(false) }
                    menuUIState.chosenRecipes[recipe] = state
                }
                CheckButton(state) {
                    onEvent(MenuEvent.ActionSelect(week.on.a.plate.screens.menu.event.SelectedEvent.CheckRecipe(recipe)))
                }
                Spacer(modifier = Modifier.width(10.dp))
            }else{
                Spacer(modifier = Modifier.width(20.dp))
            }
            if (recipe.recipe.image.startsWith("http")){
                ImageLoad(
                    recipe.recipe.image, Modifier.clip(CircleShape)
                        .clipToBounds()
                        .size(40.dp)
                        .scale(1.6f)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(
                Modifier
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
            ) {
                SubText(
                    "${recipe.portionsCount} " + stringResource(R.string.Portions)
                )
                TextBody(
                    recipe.recipe.name
                )
            }
        }
        MoreButton {
            onEvent(MenuEvent.EditPositionMore(recipe, context))
        }
        Spacer(modifier = Modifier.width(12.dp))
    }
}
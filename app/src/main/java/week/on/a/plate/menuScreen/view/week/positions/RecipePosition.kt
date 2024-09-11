package week.on.a.plate.menuScreen.view.week.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextSmall
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.menuScreen.logic.eventData.DialogMenuData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.stateData.MenuIUState
import week.on.a.plate.menuScreen.logic.eventData.NavFromMenuData
import week.on.a.plate.menuScreen.logic.eventData.SelectedData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekRecipePosition(
    recipe: Position.PositionRecipeView,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit,
) {
    Column(
        Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = { onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToFullRecipe(recipe.recipe))) },
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            //image
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                if (menuIUState.editing.value) {
                    var state = menuIUState.chosenRecipes[recipe]
                    if (state == null) {
                        state = remember { mutableStateOf(false) }
                        menuIUState.chosenRecipes[recipe] = state
                    }
                    CheckButton(state) {
                        onEvent(MenuEvent.ActionSelect(SelectedData.CheckRecipe(recipe)))
                    }
                    Spacer(modifier = Modifier.size(10.dp))
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
                MoreButtonWithBackg {
                    onEvent(MenuEvent.OpenDialog(DialogMenuData.EditPosition(recipe)))
                }
            }
        }
        SubText(
            "${recipe.portionsCount} Порции"
        )
        TextSmall(
            recipe.recipe.name
        )
    }
}
package week.on.a.plate.menuScreen.view.week.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextTitle
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.ui.theme.ColorPanelLightGrey

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekIngredientPosition(
    ingredient: Position.PositionIngredientView,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    Column(
        Modifier
            .width(150.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = { onEvent(MenuEvent.Edit(ingredient)) },
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            //image
            Spacer(modifier = Modifier.size(5.dp))
            Column {
                MoreButtonWithBackg {
                    onEvent(MenuEvent.Edit(ingredient))
                }
                Spacer(modifier = Modifier.size(10.dp))

                    SubText(ingredient.ingredient.count.toString(),)
                    SubText(ingredient.ingredient.ingredientView.measure,)

            }
        }
        SubText(
            ingredient.ingredient.description
        )
        TextBody(
            ingredient.ingredient.ingredientView.name
        )
    }
}
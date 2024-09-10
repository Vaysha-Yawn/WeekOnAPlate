package week.on.a.plate.menuScreen.view.day.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IngredientPosition(
    ingredient: Position.PositionIngredientView,
    onEvent: (event: MenuEvent) -> Unit,
    rowScope: RowScope
) {
    with(rowScope){
        Row(
            Modifier
                .weight(3f)
                .combinedClickable(
                    onClick = {},
                    onLongClick =
                    { onEvent(MenuEvent.SwitchEditMode) },
                ).padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                Modifier
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
            ) {
                SubText(ingredient.ingredient.description)
                TextBody(ingredient.ingredient.ingredientView.name,)
            }
            TextBody(ingredient.ingredient.count.toString() + " " + ingredient.ingredient.ingredientView.measure,)
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}
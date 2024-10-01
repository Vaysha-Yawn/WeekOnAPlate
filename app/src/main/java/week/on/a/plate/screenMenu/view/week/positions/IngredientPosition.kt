package week.on.a.plate.screenMenu.view.week.positions

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.buttons.MoreButtonWithBackg
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screenMenu.event.MenuEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekIngredientPosition(
    ingredient: Position.PositionIngredientView,
    onEvent: (event: Event) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
            .padding(20.dp)
            .combinedClickable(
                onClick = {onEvent(MenuEvent.EditPosition(ingredient))},
                onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
            ),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            SubText(
                ingredient.ingredient.count.toString() +" "+ ingredient.ingredient.ingredientView.measure
            )
        }
        if (ingredient.ingredient.description != "") {
            SubText(
                ingredient.ingredient.description
            )
        } else {
            Spacer(modifier = Modifier.height(12.dp))
        }
        TextBody(
            ingredient.ingredient.ingredientView.name
        )
    }
}
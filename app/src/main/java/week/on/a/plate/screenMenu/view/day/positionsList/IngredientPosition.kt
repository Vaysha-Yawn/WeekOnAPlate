package week.on.a.plate.screenMenu.view.day.positionsList

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.uitools.ImageLoad
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.core.uitools.SubText
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.screenMenu.event.MenuEvent

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
                    onClick = {onEvent(MenuEvent.EditPosition(ingredient))},
                    onLongClick =
                    { onEvent(MenuEvent.SwitchEditMode) },
                ).padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            if (ingredient.ingredient.ingredientView.img.startsWith("http")){
                ImageLoad(
                    ingredient.ingredient.ingredientView.img, Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .scale(1.5f)
                )
                Spacer(modifier = Modifier.width(12.dp))
            }
            Column(
                Modifier
                    .weight(3f),
                horizontalAlignment = Alignment.Start,
            ) {
                if (ingredient.ingredient.description!=""){
                    SubText(ingredient.ingredient.description)
                }
                TextBody(ingredient.ingredient.ingredientView.name,)
            }
            val ingredientCount = getIngredientCountAndMeasure1000(ingredient.ingredient.count, ingredient.ingredient.ingredientView.measure)
            TextBody(
                ingredientCount.first +" "+ ingredientCount.second
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}
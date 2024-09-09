package week.on.a.plate.menuScreen.view.week.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.menuScreen.logic.MenuEvent
import week.on.a.plate.menuScreen.logic.MenuIUState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekCardPosition(
    position: Position,
    menuIUState: MenuIUState,
    onEvent: (event: MenuEvent) -> Unit
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        when (position) {
            is Position.PositionDraftView -> {
                WeekDraftPosition(position, menuIUState, onEvent)
            }

            is Position.PositionIngredientView -> {
                WeekIngredientPosition(position, menuIUState, onEvent)
            }

            is Position.PositionNoteView -> {
                WeekNotePosition(position, menuIUState, onEvent,)
            }

            is Position.PositionRecipeView -> {
                WeekRecipePosition(position, menuIUState, onEvent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeekRecipePosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val week = WeekDataExample
        Column {
            WeekCardPosition(week.days[0].selections[0].positions[0],
                menuIUState, {})
            WeekCardPosition(week.days[0].selections[0].positions[1],
                menuIUState, {})
            WeekCardPosition(week.days[0].selections[0].positions[2],
                menuIUState, {})
            WeekCardPosition(week.days[0].selections[0].positions[3],
                menuIUState, {})
        }
    }
}

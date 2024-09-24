package week.on.a.plate.menuScreen.view.week.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.Event
import week.on.a.plate.menuScreen.state.MenuIUState
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeekCardPosition(
    position: Position,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit
) {
    Card(
        Modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
    ) {
        when (position) {
            is Position.PositionDraftView -> {
                WeekDraftPosition(position, onEvent)
            }

            is Position.PositionIngredientView -> {
                WeekIngredientPosition(position, onEvent)
            }

            is Position.PositionNoteView -> {
                WeekNotePosition(position, onEvent)
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

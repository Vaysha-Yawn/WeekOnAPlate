package week.on.a.plate.menuScreen.view.day.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.data.example.WeekDataExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.buttons.MoreButton
import week.on.a.plate.core.dialogs.data.DialogData
import week.on.a.plate.core.mainView.mainViewModelLogic.Event
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.menuScreen.data.eventData.MenuEvent
import week.on.a.plate.menuScreen.data.stateData.MenuIUState
import week.on.a.plate.menuScreen.view.day.BlockSelection
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardPosition(
    position: Position,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit
) {
    Card(
        Modifier
            .padding(bottom = 10.dp),
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = RectangleShape,
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .combinedClickable(
                    onClick = {},
                    onLongClick = { onEvent(MenuEvent.SwitchEditMode) },
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (position) {
                is Position.PositionDraftView -> {
                    DraftPosition(position, onEvent, this)
                }

                is Position.PositionIngredientView -> {
                    IngredientPosition(position, onEvent, this)
                }

                is Position.PositionNoteView -> {
                    NotePosition(position, onEvent, this)
                }

                is Position.PositionRecipeView -> {
                    RecipePosition(position, menuIUState, onEvent, this)
                }
            }
            MoreButton {
               onEvent(MenuEvent.EditPosition(position))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRecipePosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val week = WeekDataExample
        Column {
            BlockSelection(
                week.days[0].selections[0], menuIUState
            ) {}
        }
    }
}

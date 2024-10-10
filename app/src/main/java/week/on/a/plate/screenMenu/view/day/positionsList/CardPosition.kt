package week.on.a.plate.screenMenu.view.day.positionsList

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.ingredientTomato
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.shortRecipe
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.data.dataView.week.SelectionView
import week.on.a.plate.screenMenu.event.MenuEvent
import week.on.a.plate.screenMenu.state.MenuIUState
import week.on.a.plate.screenMenu.view.day.BlockSelection
import java.time.LocalDate
import java.time.LocalTime

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
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewRecipePosition() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val posRecipe = Position.PositionRecipeView(0, shortRecipe, 2, 0)
        val posIngredient = Position.PositionIngredientView(
            0,
            IngredientInRecipeView(0, ingredientTomato, "Целый", 0),
            0
        )
        val posDraft =
            Position.PositionDraftView(0, tags.get(1).tags, ingredients.get(1).ingredientViews, 0)
        val posNote = Position.PositionNoteView(0, " Кушаю на работе", 0)
        Column {
            BlockSelection(
                SelectionView(
                    0, "Заврак", LocalDate.now(), 0, true,
                    LocalTime.of(0,0), mutableListOf(
                        posRecipe, posIngredient, posDraft, posNote,
                    )
                ), menuIUState
            ) {}
        }
    }
}

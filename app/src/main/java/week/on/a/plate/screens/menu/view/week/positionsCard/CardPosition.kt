package week.on.a.plate.screens.menu.view.week.positionsCard

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
import week.on.a.plate.core.Event
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.data.dataView.example.ingredientTomato
import week.on.a.plate.data.dataView.example.ingredients
import week.on.a.plate.data.dataView.example.shortRecipe
import week.on.a.plate.data.dataView.example.tags
import week.on.a.plate.data.dataView.recipe.IngredientInRecipeView
import week.on.a.plate.data.dataView.week.Position
import week.on.a.plate.screens.menu.state.MenuIUState


@Composable
fun WeekCardPosition(
    position: Position, modifier: Modifier = Modifier,
    menuIUState: MenuIUState,
    onEvent: (event: Event) -> Unit
) {
    Card(
        modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
fun PreviewWeekCards() {
    WeekOnAPlateTheme {
        val menuIUState = MenuIUState.MenuIUStateExample
        val posRecipe = Position.PositionRecipeView(0, shortRecipe, 2, 0)
        val posIngredient = Position.PositionIngredientView(
            0,
            IngredientInRecipeView(0, ingredientTomato, "", 0),
            0
        )
        val posDraft = Position.PositionDraftView(0, tags.get(1).tags, ingredients.get(1).ingredientViews, 0)
        val posNote = Position.PositionNoteView(0, " Кушаю на работе",0)
        Column {
            WeekCardPosition(posRecipe,
                menuIUState= menuIUState, ){}
            WeekCardPosition(posIngredient,
                menuIUState=  menuIUState, ){}
            WeekCardPosition(posDraft,
                menuIUState= menuIUState, ){}
            WeekCardPosition(posNote,
                menuIUState=  menuIUState, ){}
        }
    }
}

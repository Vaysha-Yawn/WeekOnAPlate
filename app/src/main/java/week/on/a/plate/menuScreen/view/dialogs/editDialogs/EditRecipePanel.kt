package week.on.a.plate.menuScreen.view.dialogs.editDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.data.example.positionRecipeExample
import week.on.a.plate.core.data.week.Position
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.menuScreen.logic.eventData.ActionMenuDBData
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.NavFromMenuData
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipePositionDialogContent(recipe: Position.PositionRecipeView, onEvent: (MenuEvent) -> Unit) {
    Column(modifier = Modifier.padding(20.dp)) {
        val state = rememberDatePickerState()
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ButtonRow(
            R.drawable.add_shopping_cart,
            stringResource(R.string.add_shopping_cart),
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.OpenDialog(DialogData.ToShopList(recipe, onEvent)))
        }

        ButtonRow(
            R.drawable.add,
            stringResource(R.string.doubleR),
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.DoublePositionToMenu(recipe)))
        }

        ButtonRow(
            R.drawable.delete,
            stringResource(R.string.delete),
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.ActionDBMenu(ActionMenuDBData.Delete(recipe)))
        }

        ButtonRow(
            R.drawable.find_replace,
            stringResource(R.string.change_recipe),
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.FindReplaceRecipe(recipe)))
        }

        ButtonRow(
            R.drawable.numbers,
            stringResource(R.string.Change_number_of_servings)
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(
                MenuEvent.OpenDialog(
                    DialogData.ChangePortionsCount(recipe, stateBottom, onEvent)
                )
            )
        }

        ButtonRow(
            R.drawable.back_key,
            stringResource(R.string.move),
        ) {
            onEvent(MenuEvent.CloseDialog)
            onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.MovePositionToMenu(recipe)))
        }
    }
}

@Composable
fun ButtonRow(imgRec: Int, text: String, event: () -> Unit) {
    Row(modifier = Modifier
        .padding(vertical = 10.dp)
        .clickable { event() }) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(24.dp)
        )
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun PreviewEditRecipePanel() {
    WeekOnAPlateTheme {
        EditRecipePositionDialogContent(positionRecipeExample, {})
    }
}
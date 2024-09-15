package week.on.a.plate.menuScreen.view.dialogs.editDialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.menuScreen.logic.eventData.DialogData
import week.on.a.plate.menuScreen.logic.eventData.MenuEvent
import week.on.a.plate.menuScreen.logic.eventData.NavFromMenuData
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPositionDialogContent(selId: Long?, onEvent: (MenuEvent) -> Unit) {
    val state = rememberDatePickerState()
    Column(modifier = Modifier.padding(20.dp)) {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ButtonRowPosition(
            R.drawable.add_recipe,
            stringResource(R.string.add_recipe),
        ) {
            eventWrapper(selId, onEvent, state){ id->
                onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToAddRecipe(id)))
            }
        }

        ButtonRowPosition(
            R.drawable.add_food,
            stringResource(R.string.add_ingredient),
        ) {
            eventWrapper(selId, onEvent, state){ id->
                onEvent(MenuEvent.OpenDialog(DialogData.AddIngredient(id, stateBottom, onEvent)))
            }
        }

        ButtonRowPosition(
            R.drawable.add_draft,
            stringResource(R.string.add_draft),
        ) {
            eventWrapper(selId, onEvent, state){ id->
                onEvent(MenuEvent.NavigateFromMenu(NavFromMenuData.NavToCreateDraft))
            }
        }

        ButtonRowPosition(
            R.drawable.add_note,
            stringResource(R.string.add_note),
        ) {
            eventWrapper(selId, onEvent, state){ id->
                onEvent(MenuEvent.OpenDialog(DialogData.AddNote(id, stateBottom, onEvent)))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
private fun eventWrapper(
    selId: Long?,
    onEvent: (MenuEvent) -> Unit,
    state: DatePickerState,
    event: (Long) -> Unit,
) {
    onEvent(MenuEvent.CloseDialog)
    if (selId != null) {
        event(selId)
    } else {
        onEvent(MenuEvent.OpenDialog(DialogData.SpecifyDate(state, onEvent) { newId ->
            event(newId)
        }))
    }
}

@Composable
fun ButtonRowPosition(imgRec: Int, text: String, event: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .clickable { event() }, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = imgRec),
            contentDescription = text,
            modifier = Modifier
                .padding(end = 12.dp)
                .size(36.dp)
        )
        TextBody(text = text)
    }
    HorizontalDivider(Modifier, 1.dp, MaterialTheme.colorScheme.onSurface)
}

@Preview(showBackground = true)
@Composable
fun PreviewAddPosition() {
    WeekOnAPlateTheme {
        AddPositionDialogContent(null) {}
    }
}
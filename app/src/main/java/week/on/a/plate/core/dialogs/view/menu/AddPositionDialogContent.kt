package week.on.a.plate.core.dialogs.view.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.dialogs.DialogType
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.mainView.mainViewModelLogic.MainEvent
import week.on.a.plate.filters.navigation.FilterRoute
import week.on.a.plate.search.navigation.SearchRoute
import week.on.a.plate.ui.theme.WeekOnAPlateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPositionDialogContent(selId: Long, onEvent: (MainEvent) -> Unit) {

    Column(modifier = Modifier.padding(20.dp)) {
        val stateBottom = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ButtonRowPosition(
            R.drawable.add_recipe,
            stringResource(R.string.add_recipe),
        ) {
            eventWrapper(selId, onEvent){ id->
                onEvent(MainEvent.Navigate(SearchRoute.SearchWithSelId(id)))
            }
        }

        ButtonRowPosition(
            R.drawable.add_food,
            stringResource(R.string.add_ingredient),
        ) {
           /* eventWrapper(selId, onEvent){ id->
                onEvent(MainEvent.OpenDialog(DialogType.CreateIngredientPosition))
            }*/
        }

        ButtonRowPosition(
            R.drawable.add_draft,
            stringResource(R.string.add_draft),
        ) {
            eventWrapper(selId, onEvent){ id->
                onEvent(MainEvent.Navigate(FilterRoute.FilterToCreateDraftWithSelIdDestination(id)))
            }
        }

        ButtonRowPosition(
            R.drawable.add_note,
            stringResource(R.string.add_note),
        ) {
           /* eventWrapper(selId, onEvent){ id->
                onEvent(MainEvent.OpenDialog(DialogType.CreateNote))
            }*/
        }
    }
}


private fun eventWrapper(
    selId: Long,
    onEvent: (MainEvent) -> Unit,
    event: (Long) -> Unit,
) {
    onEvent(MainEvent.CloseDialog)
    event(selId)
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
        AddPositionDialogContent(0) {}
    }
}
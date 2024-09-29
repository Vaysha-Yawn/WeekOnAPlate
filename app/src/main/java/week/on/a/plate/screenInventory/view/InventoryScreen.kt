package week.on.a.plate.screenInventory.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.state.InventoryUIState


@Composable
fun InventoryScreen(
    state: InventoryUIState,
    onEvent: (InventoryEvent) -> Unit,
    onEventMain: (MainEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            val messageError = ""
            DoneButton(stringResource(id = R.string.done)) {
                if (state.date.value != null && (state.checkWeek.value || state.checkDayCategory.value != null)) {
                    onEvent(InventoryEvent.Done)
                } else {
                    onEventMain(MainEvent.ShowSnackBar(messageError))
                }
            }
        }
    ) { innerPadding ->

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CloseButton { onEvent(InventoryEvent.Back) }
            TextTitleItalic(
                text = "Инвентаризация",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }

        

        innerPadding
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {
        InventoryScreen(InventoryUIState(), {}) {}
    }
}
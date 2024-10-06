package week.on.a.plate.screenInventory.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.ColorButtonNegativeGrey
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CheckButton
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.state.InventoryPositionData
import week.on.a.plate.screenInventory.state.InventoryUIState


@Composable
fun InventoryScreen(
    state: InventoryUIState,
    onEvent: (InventoryEvent) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            DoneButton(stringResource(id = R.string.done)) {
                onEvent(InventoryEvent.Done)
            }
        }
    ) { innerPadding ->
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
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

            LazyColumn {
                items(state.list.value.size) {
                    CardInventory(state.list.value[it], onEvent)
                }
            }
        }
        innerPadding
    }
}

@Composable
fun CardInventory(data: InventoryPositionData, onEvent: (InventoryEvent) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val checkState = remember{ mutableStateOf(false)}
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            TextBody(text = data.ingredient.name)
            TextBodyDisActive(text = data.countTarget.toString() + " " + data.ingredient.measure)
        }
        CheckButton(checked = checkState) {
            if (checkState.value){
                onEvent(InventoryEvent.PickCount(data, 0))
                checkState.value = false
            }else{
                onEvent(InventoryEvent.PickCount(data, data.countTarget))
                checkState.value = true
            }
        }
    }
    Spacer(modifier = Modifier.size(24.dp))
}


@Preview(showBackground = true)
@Composable
fun PreviewAddRecipe() {
    WeekOnAPlateTheme {
        InventoryScreen(InventoryUIState()) {}
    }
}
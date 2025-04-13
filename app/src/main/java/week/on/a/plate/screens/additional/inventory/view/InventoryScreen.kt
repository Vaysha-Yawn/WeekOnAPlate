package week.on.a.plate.screens.additional.inventory.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import week.on.a.plate.R
import week.on.a.plate.core.theme.WeekOnAPlateTheme
import week.on.a.plate.core.uitools.TextBody
import week.on.a.plate.core.uitools.TextBodyDisActive
import week.on.a.plate.core.uitools.TextTitleItalic
import week.on.a.plate.core.uitools.buttons.CloseButton
import week.on.a.plate.core.uitools.buttons.DoneButton
import week.on.a.plate.core.utils.getIngredientCountAndMeasure1000
import week.on.a.plate.screens.additional.inventory.event.InventoryEvent
import week.on.a.plate.screens.additional.inventory.state.InventoryPositionData
import week.on.a.plate.screens.additional.inventory.state.InventoryUIState


@Composable
fun InventoryScreen(
    state: InventoryUIState,
    onEvent: (InventoryEvent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CloseButton { onEvent(InventoryEvent.Back) }
            TextTitleItalic(
                text = stringResource(R.string.inventory),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End
            )
        }
        TextBodyDisActive(
            stringResource(R.string.select_items_for_add_to_shopping_list),
            Modifier
                .padding(start = 24.dp)
                .padding(bottom = 24.dp)
        )
        LazyColumn(Modifier.weight(1f)) {
            items(
                items = state.list.value,
                key = { it.ingredient.ingredientId }) { inventoryPositionData ->
                CardInventory(inventoryPositionData, onEvent)
            }
        }
        DoneButton(stringResource(id = R.string.done), Modifier.padding(24.dp)) {
            onEvent(InventoryEvent.Done)
        }
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
        val checkState = data.answer
        Column(Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
            TextBody(text = data.ingredient.name)
            val valueAndMeasure = getIngredientCountAndMeasure1000(LocalContext.current, data.countTarget, data.ingredient.measure)
            TextBodyDisActive(text = "${valueAndMeasure.first} ${valueAndMeasure.second}")
        }
        Checkbox(
            checked = checkState.value,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                checkmarkColor = MaterialTheme.colorScheme.onBackground
            ),
            onCheckedChange = {
                if (checkState.value) {
                    onEvent(InventoryEvent.PickCount(data, 0))
                    checkState.value = false
                } else {
                    onEvent(InventoryEvent.PickCount(data, data.countTarget))
                    checkState.value = true
                }
            },
        )
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
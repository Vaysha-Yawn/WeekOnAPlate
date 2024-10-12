package week.on.a.plate.screens.inventory.view

import androidx.compose.runtime.Composable
import week.on.a.plate.screens.inventory.event.InventoryEvent
import week.on.a.plate.screens.inventory.logic.InventoryViewModel

@Composable
fun InventoryStart(
    viewModel: InventoryViewModel,
) {
    val onEvent = { eventData: InventoryEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    InventoryScreen(state, onEvent)
}
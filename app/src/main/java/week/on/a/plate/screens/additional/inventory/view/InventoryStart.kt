package week.on.a.plate.screens.additional.inventory.view

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import week.on.a.plate.screens.additional.inventory.event.InventoryEvent
import week.on.a.plate.screens.additional.inventory.logic.InventoryViewModel

@Composable
fun InventoryStart(
    viewModel: InventoryViewModel = viewModel()
) {
    val onEvent = { eventData: InventoryEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    InventoryScreen(state, onEvent)
}
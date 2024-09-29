package week.on.a.plate.screenInventory.view

import androidx.compose.runtime.Composable
import week.on.a.plate.mainActivity.event.MainEvent
import week.on.a.plate.mainActivity.logic.MainViewModel
import week.on.a.plate.screenInventory.event.InventoryEvent
import week.on.a.plate.screenInventory.logic.InventoryViewModel

@Composable
fun InventoryStart(
    mainViewModel: MainViewModel,
    viewModel: InventoryViewModel,
) {
    viewModel.mainViewModel = mainViewModel

    val onEvent = { eventData: InventoryEvent ->
        viewModel.onEvent(eventData)
    }
    val onEventMain = { eventData: MainEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    InventoryScreen(state, onEvent, onEventMain)
}
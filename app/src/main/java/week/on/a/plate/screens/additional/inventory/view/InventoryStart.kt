package week.on.a.plate.screens.additional.inventory.view

import androidx.compose.runtime.Composable
import week.on.a.plate.app.mainActivity.logic.MainViewModel
import week.on.a.plate.app.mainActivity.view.MainEventResolve
import week.on.a.plate.screens.additional.inventory.event.InventoryEvent
import week.on.a.plate.screens.additional.inventory.logic.InventoryViewModel

@Composable
fun InventoryStart(
    viewModel: InventoryViewModel,
    viewModel1: MainViewModel
) {
    val onEvent = { eventData: InventoryEvent ->
        viewModel.onEvent(eventData)
    }
    val state = viewModel.state
    InventoryScreen(state, onEvent)
    MainEventResolve(viewModel.mainEvent, viewModel.dialogOpenParams, viewModel1)
}
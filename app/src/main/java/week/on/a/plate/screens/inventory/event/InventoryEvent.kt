package week.on.a.plate.screens.inventory.event

import week.on.a.plate.core.Event
import week.on.a.plate.screens.inventory.state.InventoryPositionData

sealed class InventoryEvent : Event() {
    data object Done : InventoryEvent()
    data object Back : InventoryEvent()
    data class PickCount(val inventoryCategory: InventoryPositionData, val count:Int) : InventoryEvent()
}
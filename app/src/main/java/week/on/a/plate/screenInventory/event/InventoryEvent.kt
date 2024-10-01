package week.on.a.plate.screenInventory.event

import week.on.a.plate.core.Event
import week.on.a.plate.screenInventory.state.InventoryPositionData

sealed class InventoryEvent : Event() {
    data object Done : InventoryEvent()
    data object Back : InventoryEvent()
    data class PickCount(val inventoryCategory: InventoryPositionData, val count:Int) : InventoryEvent()
}
package week.on.a.plate.screens.additional.inventory.event

import week.on.a.plate.core.Event
import week.on.a.plate.screens.additional.inventory.state.InventoryPositionData

sealed interface InventoryEvent : Event {
    object Done : InventoryEvent
    object Back : InventoryEvent
    class PickCount(val inventoryCategory: InventoryPositionData, val count: Int) : InventoryEvent
}
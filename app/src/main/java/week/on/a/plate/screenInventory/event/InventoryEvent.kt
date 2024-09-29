package week.on.a.plate.screenInventory.event

import week.on.a.plate.core.Event

sealed class InventoryEvent : Event() {
    data object Done : InventoryEvent()
    data object Back : InventoryEvent()
    data object ChooseDate : InventoryEvent()
}
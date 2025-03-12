package week.on.a.plate.dialogs.changePortions.event

import week.on.a.plate.core.Event

sealed interface ChangePortionsCountEvent : Event {
    object Done : ChangePortionsCountEvent
    object Close : ChangePortionsCountEvent
}
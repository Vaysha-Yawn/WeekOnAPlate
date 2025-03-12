package week.on.a.plate.dialogs.editOneString.event

import week.on.a.plate.core.Event

sealed interface EditOneStringEvent : Event {
    object Done : EditOneStringEvent
    object Close : EditOneStringEvent
}
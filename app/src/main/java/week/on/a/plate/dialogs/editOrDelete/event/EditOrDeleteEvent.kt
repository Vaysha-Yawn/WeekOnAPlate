package week.on.a.plate.dialogs.editOrDelete.event

import week.on.a.plate.core.Event

sealed interface EditOrDeleteEvent : Event {
    object Edit : EditOrDeleteEvent
    object Delete : EditOrDeleteEvent
    object Close : EditOrDeleteEvent
}
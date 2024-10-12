package week.on.a.plate.dialogs.editOrDelete.event

import week.on.a.plate.core.Event

sealed class EditOrDeleteEvent: Event() {
    data object Edit: EditOrDeleteEvent()
    data object Delete: EditOrDeleteEvent()
    data object Close: EditOrDeleteEvent()
}
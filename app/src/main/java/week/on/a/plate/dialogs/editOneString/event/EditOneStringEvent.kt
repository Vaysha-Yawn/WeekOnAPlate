package week.on.a.plate.dialogs.editOneString.event

import week.on.a.plate.core.Event

sealed class EditOneStringEvent: Event() {
    data object Done: EditOneStringEvent()
    data object Close: EditOneStringEvent()
}
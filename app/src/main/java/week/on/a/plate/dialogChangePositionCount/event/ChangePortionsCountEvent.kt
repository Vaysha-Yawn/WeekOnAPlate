package week.on.a.plate.dialogChangePositionCount.event

import week.on.a.plate.core.Event

sealed class ChangePortionsCountEvent: Event() {
    data object Done: ChangePortionsCountEvent()
    data object Close: ChangePortionsCountEvent()
}
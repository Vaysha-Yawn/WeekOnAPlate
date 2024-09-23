package week.on.a.plate.core.dialogs.dialogAbstract.event


import week.on.a.plate.core.Event

sealed class DialogEvent: Event() {
    data object Done: DialogEvent()
    data object Close: DialogEvent()
}
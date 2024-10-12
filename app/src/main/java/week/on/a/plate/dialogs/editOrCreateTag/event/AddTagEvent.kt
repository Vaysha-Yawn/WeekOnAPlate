package week.on.a.plate.dialogs.editOrCreateTag.event


import week.on.a.plate.core.Event

sealed class AddTagEvent: Event() {
    data object Done: AddTagEvent()
    data object Close: AddTagEvent()
    data object ChooseCategory: AddTagEvent()
}
package week.on.a.plate.screens.additional.filters.dialogs.editOrCreateTag.event


import week.on.a.plate.core.Event

sealed interface AddTagEvent : Event {
    object Done : AddTagEvent
    object Close : AddTagEvent
    object ChooseCategory : AddTagEvent
}
package week.on.a.plate.dialogs.editOrCreateTag.event


import week.on.a.plate.core.Event

sealed interface EditOrCreateTagEvent : Event {
    object Done : EditOrCreateTagEvent
    object Close : EditOrCreateTagEvent
    object ChooseCategory : EditOrCreateTagEvent
}
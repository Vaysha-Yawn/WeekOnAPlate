package week.on.a.plate.dialogs.forSearchScreen.filtersMore.event


import week.on.a.plate.core.Event

sealed interface FiltersMoreEvent : Event {
    object Close : FiltersMoreEvent
}
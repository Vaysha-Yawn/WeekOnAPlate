package week.on.a.plate.dialogs.forSearchScreen.sortMore.event


import week.on.a.plate.core.Event

sealed interface SortMoreEvent : Event {
    object AlphabetNormal : SortMoreEvent
    object AlphabetRevers : SortMoreEvent
    object DateFromOldToNew : SortMoreEvent
    object DateFromNewToOld : SortMoreEvent
    object Random : SortMoreEvent
    object Close : SortMoreEvent
}